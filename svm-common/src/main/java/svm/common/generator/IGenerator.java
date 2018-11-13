package svm.common.generator;

public interface IGenerator {
    String generateString(String... parameters);

    Long generateLong(String... parameters);
}
