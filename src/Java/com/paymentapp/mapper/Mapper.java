package Java.com.paymentapp.mapper;

public interface Mapper<F, T> {
    T toEntity(F from);
}
