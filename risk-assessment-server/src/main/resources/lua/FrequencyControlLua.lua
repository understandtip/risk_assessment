local key = KEYS[1]
local expire = ARGV[1]
local count = ARGV[2]

if redis.call('EXISTS', key) == 0 then
    -- key不存在

    redis.call('SETEX', key, expire, 1)
    return 1
else
    local currentCount = redis.call("GET", key)
    if tonumber(currentCount) + 1 > tonumber(count) then
        return 0
    else
        redis.call("INCRBY", key, "1")
        return 1
    end
end