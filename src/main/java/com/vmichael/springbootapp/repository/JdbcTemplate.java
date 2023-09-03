package com.vmichael.springbootapp.repository;

import com.vmichael.springbootapp.utils.ObjectsUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Обертка для Jdbc. Реализуюет следующую идеи: я не хочу никому отдавать. Hi FP.
 * {@link java.sql.Connection}, поэтому спрашиваю у пользователя "что он хочет с ним сделать?".
 */
public class JdbcTemplate implements AutoCloseable {
  /**
   * Обертка над {@link java.util.function.Function}.
   * @param <T> тип аргумента
   * @param <R> тип отображения
   */
  @FunctionalInterface
  public interface SQLFunction<T, R> {
    /**
     * Функция.
     * @param object аргумент
     * @return отображение
     * @throws SQLException если произошла ошибка на уровне БД
     */
    R apply(T object) throws SQLException;
  }

  /**
   * Обертка над {@link java.util.function.Consumer}.
   * @param <T> тип аргумента
   */
  @FunctionalInterface
  public interface SQLConsumer<T> {
    /**
     * Процедура.
     * @param object аргумент
     * @throws SQLException если произошла ошибка на уровне БД
     */
    void accept(T object) throws SQLException;
  }

  /**
   * Подлючение к БД
   */
  private final Connection connection;

  /**
   * Геттер {@link #connection}. Не поддерживается, нельзя переопределять.
   * @throws UnsupportedOperationException всегда
   */
  public final Connection getConnection() {
    throw new UnsupportedOperationException();
  }

  /**
   * Получение подлючения через url.
   * @param url вашей БД
   * @throws SQLException если произошла ошибка на уровне БД
   */
  public JdbcTemplate(final String url) throws SQLException {
    this.connection = DriverManager.getConnection(Objects.requireNonNull(url));
  }

  /**
   * Получение подключения через url, username и pass
   * @param url вашей бд
   * @param userName имя пользователя
   * @param pass пароль
   * @throws SQLException если произошла ошибка на уровне БД
   */
  public JdbcTemplate(final String url, final String userName, final String pass) throws SQLException {
    this.connection = DriverManager.getConnection(url, userName, pass);
  }

  /**
   * Выполняет sql запрос. Запрос должен быть готовым.
   * Лучше будет убрать, не очень хорошо, выполнять любой запрос
   * @param query sql запрос
   * @throws SQLException если произошла ошибка на уровне БД или ошибка в запросе
   */
  public void executeQuery(final String query) throws SQLException {
    Objects.requireNonNull(query);
    try (final Statement statement = connection.createStatement()) {
      statement.execute(query);
    }
  }

  /**
   * Выполняет Sql запрос. Использует вашу функцию, которая принимает {@link ResultSet}
   * @param query sql запрос
   * @param sqlFunction функция, что вы хотите сделать с {@link #connection}
   * @return результат sql запроса
   * @param <T> тип результата sql запроса
   * @throws SQLException если произошла ошибка на уровне БД или ошибка в запросе
   */
  public <T> T execute(final String query, final SQLFunction<? super ResultSet, ? extends T> sqlFunction)
   throws SQLException {
    ObjectsUtils.throwNPEIfNull(query, sqlFunction);
    try (final Statement statement = connection.createStatement();
         final ResultSet resultSet = statement.executeQuery(query)
    ) {
      return sqlFunction.apply(resultSet);
    }
  }

  /**
   * Выполняет Sql запрос. Использует вашу процедуру, которая принимает {@link PreparedStatement}
   * @param query sql запрос
   * @param sqlConsumer процедура, что вы хотите сделать с {@link #connection}
   * @throws SQLException если произошла ошибка на уровне БД или ошибка в запросе
   */
  public void executePreparedQuery(final String query, final SQLConsumer<? super PreparedStatement> sqlConsumer)
   throws SQLException {
    ObjectsUtils.throwNPEIfNull(query, sqlConsumer);
    try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      sqlConsumer.accept(preparedStatement);
    }
  }

  /**
   * Выполняет Sql запрос. Использует вашу процедуру, которая принимает {@link ResultSet}
   * @param query sql запрос
   * @param sqlConsumer процедура, что вы хотите сделать с {@link #connection}
   * @throws SQLException если произошла ошибка на уровне БД или ошибка в запросе
   */
  public void executeQuery(final String query, final SQLConsumer<? super ResultSet> sqlConsumer)
   throws SQLException {
    ObjectsUtils.throwNPEIfNull(query, sqlConsumer);
    try (final Statement statement = connection.createStatement()) {
      sqlConsumer.accept(statement.executeQuery(query));
    }
  }

  /**
   * Выполняет Sql запрос. Использует вашу функцию, которая принимает {@link PreparedStatement}
   * и функцию, которая принимает {@link ResultSet}
   * @param query sql запрос
   * @param sqlConsumer процедура, что вы хотите сделать с {@link #connection}
   * @param sqlFunction функция, что вы хотите сделать с {@link #connection}
   * @param <T> тип результата sql запроса
   * @throws SQLException если произошла ошибка на уровне БД или ошибка в запросе
   */
  public <T> void executePreparedQuery(
   final String query,
   final SQLConsumer<? super PreparedStatement> sqlConsumer,
   final SQLConsumer<? super ResultSet> sqlFunction) throws SQLException {
    ObjectsUtils.throwNPEIfNull(query, sqlConsumer, sqlFunction);
    try (final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      sqlConsumer.accept(preparedStatement);
      try (final ResultSet resultSet = preparedStatement.executeQuery()) {
        sqlFunction.accept(resultSet);
      }
    }
  }

  /**
   * @see AutoCloseable
   * @throws SQLException если произошла ошибка на уровне БД
   * @throws IllegalStateException если {@link #connection} уже закрыт
   */
  @Override
  public void close() throws SQLException {
    if (connection.isClosed()) {
      throw new IllegalStateException("Connection is closed");
    }
    connection.close();
  }
}
