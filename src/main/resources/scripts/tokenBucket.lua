local key = KEYS[1]

local capacity = tonumber(ARGV[1])
local refillRate = tonumber(ARGV[2])
local now = tonumber(ARGV[3])
local requested = tonumber(ARGV[4])

local bucket = redis.call("HMGET", key, "tokens", "lastRefill")
local tokens = tonumber(bucket[1])
local lastRefill = tonumber(bucket[2])

-- First time we've ever seen this identity: bucket starts full
if tokens == nil then
    tokens = capacity
    lastRefill = now
end

--elapsed means how much time passed since the last request was made.
local elapsed = math.max(0, now - lastRefill)

tokens = math.min(capacity, tokens + (elapsed * refillRate))

local allowed = 0
if tokens >= requested then
    tokens = tokens - requested
    allowed = 1
end

redis.call("HMSET", key, "tokens", tokens, "lastRefill", now)
redis.call("EXPIRE", key, 3600)

return allowed