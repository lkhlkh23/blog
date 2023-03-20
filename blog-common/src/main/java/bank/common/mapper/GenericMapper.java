package bank.common.mapper;

public interface GenericMapper<F, T> {

    T from(F from);

}
