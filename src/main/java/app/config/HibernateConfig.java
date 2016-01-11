package app.config;

import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	@Bean
	public SessionFactory sessionFactory(BasicDataSource basicDataSource) {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(
				basicDataSource);

		builder.scanPackages("app.*").addProperties(
				getHibernateProperties());

		return builder.buildSessionFactory();
	}

	@Bean
	@Autowired
	public HibernateTransactionManager txManager(BasicDataSource basicDataSource) {
		return new HibernateTransactionManager(sessionFactory(basicDataSource));
	}

	private Properties getHibernateProperties() {
		Properties prop = new Properties();
		prop.put("hibernate.format_sql", "false");
		prop.put("hibernate.show_sql", "false");
		prop.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

		return prop;
	}
}