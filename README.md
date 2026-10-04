RateLimiterApp — Distributed Rate Limiting System

A rate limiter is a system that controls how many API requests a user can make.

A distributed, multi-tenant rate-limiting backend built with Java, Spring Boot, Redis, Lua, and MySQL. The system implements the Token Bucket algorithm to control request rates using configurable policies, API-key authentication, and shared Redis state.

Overview

RateLimiterApp allows multiple tenant applications to create independent rate-limit policies and check whether a request should be allowed or rejected. Redis maintains runtime token-bucket state, while MySQL stores tenant, credential, and policy information.

The system uses Redis Lua scripts to perform token-bucket operations atomically, helping maintain consistent rate-limit decisions when multiple requests arrive concurrently.

Architecture
                 Tenant Application
                         |
                         | API Key
                         | Policy ID + Identity
                         v
                Spring Boot Backend
                         |
              +----------+----------+
              |                     |
              v                     v
        API Authentication     Policy Management
              |                     |
              +----------+----------+
                         |
                         v
                  Rate Limit Service
                         |
                         v
                Token Bucket Algorithm
                         |
                    Redis + Lua
                         |
                         v
                  ALLOW / REJECT

        MySQL: Tenants, Credentials, Policies
        Redis: Runtime Token Bucket State
Key Features
Distributed Rate Limiting: Uses Redis as shared runtime storage rather than relying solely on application-local memory.
Token Bucket Algorithm: Supports configurable bucket capacity and token refill rate.
Atomic State Management: Uses Redis Lua scripts to atomically refill tokens, consume tokens, and update bucket state.
Multi-Tenant Architecture: Associates API credentials and rate-limit policies with individual tenants.
API-Key Authentication: Identifies tenants through API keys supplied in HTTP request headers.
Policy Management: Allows tenants to create policies with configurable capacity and refill rate.
Per-Identity Rate Limiting: Maintains separate buckets for distinct tenant, policy, and identity combinations.
RESTful API Design: Exposes HTTP endpoints for tenant registration, policy creation, and rate-limit checks.
Technology Stack
Technology	Purpose
Java 21	Core programming language
Spring Boot	Backend application and REST APIs
Spring Web	HTTP request handling
Spring Data JPA	Persistence and ORM
MySQL	Tenant, credential, and policy storage
Redis	Shared rate-limit state
Lua	Atomic token-bucket execution
Maven	Dependency management and build
Postman	API testing
How the Token Bucket Algorithm Works

Each bucket is identified by a combination of tenant, policy, and request identity.

A bucket maintains two primary values:

tokens: The currently available tokens.
lastRefill: The timestamp used to calculate token replenishment.

For each request, the algorithm:

Calculates elapsed time since the last refill timestamp.
Calculates the tokens generated using the configured refill rate.
Adds available tokens without exceeding the bucket capacity.
Checks whether enough tokens exist for the request.
Consumes a token if the request is allowed.
Persists the updated state atomically in Redis.
Example

Consider a policy configured as follows:

Capacity:       10 tokens
Refill rate:    0.1 tokens/second
Request cost:   1 token
Scenario	Expected result
First 10 rapid requests	Allowed
11th rapid request	Rejected
After approximately 10 seconds	One token becomes available
Next request with a token available	Allowed

The bucket never stores more than its configured capacity. Fractional refill progress is preserved through timestamp-based calculations.

API Endpoints
1. Register a Tenant

POST /tenants/register

Registers a tenant and returns its API key.

Example response:

{
  "apiKey": "<generated-api-key>"
}

Keep the API key secret. In a production deployment, credentials should be stored securely and raw API keys should not be persisted in plaintext.

2. Create a Rate-Limit Policy

POST /api/policies

Request header:

Authorization: <API_KEY>

Request body:

{
  "capacity": 10,
  "refillRate": 0.1
}

Example response:

{
  "policyId": "<generated-policy-uuid>",
  "capacity": 10,
  "refillRate": 0.1
}

The backend identifies the tenant from the API key and associates the new policy with that tenant.

3. Check a Request

POST /api/rate-limit

Request header:

Authorization: <API_KEY>

Example request body:

{
  "policyId": "<policy-uuid>",
  "identity": "user-123"
}

The backend validates the API key, resolves the tenant, verifies that the policy belongs to that tenant, and evaluates the request against the selected token bucket.

Allowed response:

{
  "allowed": true
}

Rejected response:

{
  "allowed": false,
  "message": "Rate limit exceeded"
}

A rejected request should use HTTP 429 Too Many Requests. The exact response fields depend on the response DTO configured in the application.

Data Storage Design
MySQL

Relational storage manages relatively persistent application data:

Tenants: Registered client organizations.
API Credentials: Credentials associated with tenants.
Rate-Limit Policies: Capacity, refill rate, and tenant association.
Redis

Redis maintains the runtime state of active token buckets.

Example key:

rateLimit:<tenantId>:<policyId>:<identity>

Example hash:

tokens      -> 7
lastRefill  -> <timestamp-in-milliseconds>

Inactive bucket keys expire automatically according to the configured expiration time.
