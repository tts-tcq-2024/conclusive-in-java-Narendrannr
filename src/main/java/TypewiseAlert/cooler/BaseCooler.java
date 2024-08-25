package TypewiseAlert.cooler;

public class BaseCooler implements CoolerInterface {
	int mLowerLimit = 0;
    int mUpperLimit = 0;

	@Override
	public int getUpperLimit() {
		// TODO Auto-generated method stub
		return mUpperLimit;
	}

	@Override
	public int getLowerLimit() {
		// TODO Auto-generated method stub
		return mLowerLimit;
	}

}
