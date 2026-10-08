## Before Caching — Fixed IP Test

**JMeter Configuration:** 50 users, 5s ramp-up, 30s duration, single IP address.

| Metric     |             Result |
| ---------- | -----------------: |
| Throughput | **7,700.94 req/s** |
| P95        |          **11 ms** |
| P99        |          **16 ms** |
| Error Rate |          **0.00%** |

These results are the **baseline** for comparison after implementing Caffeine caching.


## Before Caching — Random IP Test

**JMeter Configuration:** 50 users, 5s ramp-up, 30s duration, randomized IP addresses.

| Metric     |             Result |
| ---------- | -----------------: |
| Throughput | **7,589.96 req/s** |s
| P95        |          **11 ms** |
| P99        |          **15 ms** |
| Error Rate |          **0.00%** |

These results are the **baseline** for comparison after implementing Caffeine caching.
