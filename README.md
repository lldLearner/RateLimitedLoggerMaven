Notes -> 

Initally I was thinking to go with AtomicInteger approach, the drawbacks that I found were as follows -> 

Lets say there are Threads T1 and T2, now get() will is suppose accessed by T1 then immeddiately it got context switched the same value will be accessed by T2, which can lead to the race condn.
I tried handling it with compareAndSet(int expect, int update) which could have kind of handled the race condition but for token acquisiton there seemed to be a need to take the lock. 

After I decided to discard this approach, I decided to go with object level locking i.e. synchronized methods and wait() and notifyAll() mechanism. 

### But I still think that synchronizeed methods make the code as good as single threaded, I may need some suggestions on this approach. Also the testing part I feel Junit i wrote is very generic and very basic how to make it production grade to test concurrency!

This is how the output looked like - 
```
Message logged by thread pool-1-thread-1 messageNumber 0
Message logged by thread pool-1-thread-2 messageNumber 1
Message logged by thread pool-1-thread-3 messageNumber 2
Message logged by thread pool-1-thread-2 messageNumber 4
Message logged by thread pool-1-thread-1 messageNumber 3
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 6 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 5 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 7 went into waiting mode![0m
Message logged by thread pool-1-thread-2 messageNumber 6
Message logged by thread pool-1-thread-2 messageNumber 8
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 6 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-1 messageNumber 7
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 7 came out of waiting mode, now publishing log![0m
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 5 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-3 messageNumber 5
Message logged by thread pool-1-thread-1 messageNumber 10
Message logged by thread pool-1-thread-2 messageNumber 9
Message logged by thread pool-1-thread-1 messageNumber 12
Message logged by thread pool-1-thread-1 messageNumber 14
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 15 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 11 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 13 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 15 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-1 messageNumber 15
Message logged by thread pool-1-thread-2 messageNumber 13
Message logged by thread pool-1-thread-3 messageNumber 11
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 13 came out of waiting mode, now publishing log![0m
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 11 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-2 messageNumber 17
Message logged by thread pool-1-thread-1 messageNumber 16
Message logged by thread pool-1-thread-2 messageNumber 19
Message logged by thread pool-1-thread-3 messageNumber 18
Message logged by thread pool-1-thread-2 messageNumber 21
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 23 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 20 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 22 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 23 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-2 messageNumber 23
Message logged by thread pool-1-thread-2 messageNumber 24
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 22 came out of waiting mode, now publishing log![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 30 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 20 came out of waiting mode, now publishing log![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 26 went into waiting mode![0m
Message logged by thread pool-1-thread-2 messageNumber 25
Message logged by thread pool-1-thread-3 messageNumber 22
Message logged by thread pool-1-thread-3 messageNumber 27
Message logged by thread pool-1-thread-3 messageNumber 28
Message logged by thread pool-1-thread-3 messageNumber 29
Message logged by thread pool-1-thread-1 messageNumber 20
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 31 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 30 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-3 messageNumber 30
Message logged by thread pool-1-thread-1 messageNumber 31
Message logged by thread pool-1-thread-1 messageNumber 33
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 31 came out of waiting mode, now publishing log![0m
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 26 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-1 messageNumber 34
Message logged by thread pool-1-thread-1 messageNumber 35
Message logged by thread pool-1-thread-1 messageNumber 36
Message logged by thread pool-1-thread-2 messageNumber 26
Message logged by thread pool-1-thread-1 messageNumber 37
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 39 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 32 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 38 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 39 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-1 messageNumber 39
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 38 came out of waiting mode, now publishing log![0m
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 32 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-2 messageNumber 38
Message logged by thread pool-1-thread-3 messageNumber 32
Message logged by thread pool-1-thread-2 messageNumber 41
Message logged by thread pool-1-thread-1 messageNumber 40
Message logged by thread pool-1-thread-2 messageNumber 43
Message logged by thread pool-1-thread-3 messageNumber 42
Message logged by thread pool-1-thread-2 messageNumber 45
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 44 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 47 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 46 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 44 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-1 messageNumber 44
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 46 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-1 messageNumber 48
Message logged by thread pool-1-thread-3 messageNumber 46
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 47 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-2 messageNumber 47
Message logged by thread pool-1-thread-3 messageNumber 50
Message logged by thread pool-1-thread-3 messageNumber 52
Message logged by thread pool-1-thread-1 messageNumber 49
Message logged by thread pool-1-thread-3 messageNumber 53
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 55 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 51 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 54 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 55 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-3 messageNumber 55
Message logged by thread pool-1-thread-3 messageNumber 56
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 54 came out of waiting mode, now publishing log![0m
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 51 came out of waiting mode, now publishing log![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 63 went into waiting mode![0m
Message logged by thread pool-1-thread-1 messageNumber 54
Message logged by thread pool-1-thread-1 messageNumber 58
Message logged by thread pool-1-thread-1 messageNumber 59
Message logged by thread pool-1-thread-1 messageNumber 60
Message logged by thread pool-1-thread-2 messageNumber 51
Message logged by thread pool-1-thread-2 messageNumber 62
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 61 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 57 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 63 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-2 messageNumber 63
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 57 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-3 messageNumber 57
Message logged by thread pool-1-thread-3 messageNumber 65
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 61 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-1 messageNumber 61
Message logged by thread pool-1-thread-3 messageNumber 66
Message logged by thread pool-1-thread-2 messageNumber 64
Message logged by thread pool-1-thread-3 messageNumber 68
Message logged by thread pool-1-thread-1 messageNumber 67
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 70 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 69 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 71 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 70 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-3 messageNumber 70
Message logged by thread pool-1-thread-1 messageNumber 71
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 71 came out of waiting mode, now publishing log![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 78 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 69 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-1 messageNumber 73
Message logged by thread pool-1-thread-1 messageNumber 74
Message logged by thread pool-1-thread-1 messageNumber 75
Message logged by thread pool-1-thread-1 messageNumber 76
Message logged by thread pool-1-thread-1 messageNumber 77
Message logged by thread pool-1-thread-2 messageNumber 69
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 72 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 79 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 78 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-1 messageNumber 78
Message logged by thread pool-1-thread-1 messageNumber 80
Message logged by thread pool-1-thread-1 messageNumber 81
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 79 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-2 messageNumber 79
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 72 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-3 messageNumber 72
Message logged by thread pool-1-thread-2 messageNumber 83
Message logged by thread pool-1-thread-1 messageNumber 82
Message logged by thread pool-1-thread-2 messageNumber 85
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 84 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 87 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 86 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 84 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-3 messageNumber 84
Message logged by thread pool-1-thread-3 messageNumber 88
Message logged by thread pool-1-thread-3 messageNumber 89
Message logged by thread pool-1-thread-3 messageNumber 90
Message logged by thread pool-1-thread-3 messageNumber 91
Message logged by thread pool-1-thread-3 messageNumber 92
Message logged by thread pool-1-thread-1 messageNumber 86
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 86 came out of waiting mode, now publishing log![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-1 messageNumber 94 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 87 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-2 messageNumber 87
[33m[WARNING] Message Message logged by thread pool-1-thread-2 messageNumber 95 went into waiting mode![0m
[33m[WARNING] Message Message logged by thread pool-1-thread-3 messageNumber 93 went into waiting mode![0m
[32m[INFO] Message Message logged by thread pool-1-thread-1 messageNumber 94 came out of waiting mode, now publishing log![0m
Message logged by thread pool-1-thread-1 messageNumber 94
Message logged by thread pool-1-thread-3 messageNumber 93
Message logged by thread pool-1-thread-2 messageNumber 95
Message logged by thread pool-1-thread-2 messageNumber 98
Message logged by thread pool-1-thread-2 messageNumber 99
Message logged by thread pool-1-thread-3 messageNumber 97
Message logged by thread pool-1-thread-1 messageNumber 96
[32m[INFO] Message Message logged by thread pool-1-thread-3 messageNumber 93 came out of waiting mode, now publishing log![0m
[32m[INFO] Message Message logged by thread pool-1-thread-2 messageNumber 95 came out of waiting mode, now publishing log![0m

```
