package types;


public class ResultWrap<T> {
    int resultCode;
    T result;

    public ResultWrap(int resultCode, T result) {
        this.resultCode = resultCode;
        this.result = result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public T getResult() {
        return result;
    }
}
