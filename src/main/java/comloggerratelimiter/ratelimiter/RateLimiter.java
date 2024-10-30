package comloggerratelimiter.ratelimiter;

public class RateLimiter {

	private int currTokenCount;
	private int tokenBucketCapacity;

	public RateLimiter(int currTokenCount, int tokenBucketCapacity) {
		// TODO Auto-generated constructor stub
		this.currTokenCount = currTokenCount;
		this.tokenBucketCapacity = tokenBucketCapacity;
	}

	public boolean acquireToken() {
		if (currTokenCount > 0) {
			currTokenCount--;
			return true;
		}
		return false;
	}

	public void refillToken() {
		while(currTokenCount < tokenBucketCapacity) {
			currTokenCount++;
		}
	}
}
