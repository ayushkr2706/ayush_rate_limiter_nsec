local key = KEYS[1]

local capacity = tonumber(ARGV[1])
local refillRate = tonumber(ARGV[2])        --tokens per second
local requested = tonumber(ARGV[3])
--Ask redis for current time (seconds + microseconds)
local time = redis.call("TIME")
local now = tonumber(time[1] * 1000 + math.floor(time[2]/1000))  --Current time in milliseconds

local bucket = redis.call("HMGET", key, "tokens", "lastRefill")
local tokens = tonumber(bucket[1])
local lastRefill = tonumber(bucket[2])
local retryAfter

-- First time we've ever seen this identity: bucket starts full
if tokens == nil then
    tokens = capacity
    lastRefill = now
end

--elapsed means how much time passed since the last request was made.
local elapsed = (math.max(0, now - lastRefill))/1000  -- in seconds

local tokensGenerated = elapsed * refillRate;

local availableSpace = capacity - tokens;

local wholeTokens = math.floor(tokensGenerated)

local actualTokensAdded = math.min(availableSpace, wholeTokens)

--Refilling of tokens
tokens = math.min(capacity, tokens + actualTokensAdded)

 if tokens >= capacity then
    --Bucket is full, throw away any leftover time
    --because if the bucket is full then previous time should not be carried further.
    lastRefill = now
elseif  actualTokensAdded > 0 then
    local usedTime = (actualTokensAdded/refillRate) * 1000  --in milliseconds
    lastRefill = lastRefill + usedTime
end

local allowed = 0
if tokens >= requested then
    tokens = tokens - requested
    allowed = 1
end

if allowed == 1 then
    retryAfter = 0
else
    --time needed to make the missing tokens
    local secondsNeeded = (requested - tokens)/refillRate
    --time that has already passed since lastRefill
    local secondsAlreadyPass = math.max(0, now - lastRefill)/100
    --Round up to a whole second and never return less than 1
    retryAfter = math.max(1, math.ceil(secondsNeeded - secondsAlreadyPass))
end

redis.call("HMSET", key, "tokens", tokens, "lastRefill", lastRefill)
redis.call("EXPIRE", key, 3600)

return {allowed, retryAfter, tokens}