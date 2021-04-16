public class Solution
{
	int maxPeixes;
	int dist;
	int rating;

	public void setSolution(int maxPeixes, int dist, int rating)
	{
		this.maxPeixes = maxPeixes;
		this.dist = dist;
		this.rating = rating;
	}

	public void copy(Solution s)
	{
		this.maxPeixes = s.maxPeixes;
		this.dist = s.dist;
		this.rating = s.rating;
	}

	public void inc(Solution s)
	{
		this.maxPeixes += s.maxPeixes;
		this.dist += s.dist;
		this.rating += s.rating;
	}
}