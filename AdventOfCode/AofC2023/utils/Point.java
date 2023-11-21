package utils;

public class Point extends Pair<Integer, Integer> {
    public int x = getFirst();
    public int y = getSecond();

    public Point(int x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
    }

    public Point(Point toCopy) {
        super(toCopy.x, toCopy.y);
    }

    @Override
    public void setFirst(Integer toSet) {
        x = toSet;
    }

    @Override
    public void setSecond(Integer toSet) {
        y = toSet;
    }

    @Override
    public Integer getFirst() {
        return x;
    }

    @Override
    public Integer getSecond() {
        return y;
    }

    /**
     * Distance one point to (0,0)
     * 
     * @return double
     */
    public double distance() {
        // return Math.sqrt((this.x * this.x) + ((this.y * this.y)));
        return distance(0.0, 0.0);
    }

    /**
     * Distance with two parameters (x1,y1) to (x2,y2)
     * 
     * @param x
     * @param y
     * @return double
     */
    public double distance(double x, double y) {
        return (double) Math
                .sqrt(((double) this.x - x) * ((double) this.x - x) + ((double) this.y - y) * ((double) this.y - y));
    }

    /**
     * Distance to another point
     * 
     * @param point
     * @return
     */
    public double distance(Point point) {
        // return Math.sqrt((this.x - point.x)*(this.x - point.x) + (this.y -
        // point.y)*(this.y - point.y));
        return distance((double) point.x, (double) point.y);
    }
}