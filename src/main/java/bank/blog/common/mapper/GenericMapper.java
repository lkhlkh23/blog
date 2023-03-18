package bank.blog.common.mapper;

public interface GenericMapper<F, T> {

    T from(F from);

}
