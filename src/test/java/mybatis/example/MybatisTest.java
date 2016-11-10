package mybatis.example;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * spring에서 mybatis 사용시 mapper테스트를 위한 샘플로 설정을 xml로 하지 않고 테스트 하는 방법.<br>
 * build.gradle dependencies에 compile 'org.mybatis:mybatis:3.1.1' 추가 
 * 
 * @author july
 */
public class MybatisTest {

	SqlSession session;
	
	@BeforeClass
	public void setUp(){
		String JDBC_DRIVER = "core.log.jdbc.driver.MysqlDriver";  
		String DB_URL = "jdbc:mysql://xx.xx.xxx.xxx:3306/xxxxdb";
		String USER = "xxxxx";
		String PASS = "xxxxx";
		
		DataSource dataSource = new PooledDataSource(JDBC_DRIVER, DB_URL, USER, PASS);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        
        Configuration configuration = new Configuration(environment);
        // mapper class 추가
        //configuration.addMapper(xxMapper.class);
        //configuration.addMapper(xxxxMapper.class);
        
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        session = sqlSessionFactory.openSession();
	}
	
	@Test
	public void testDao(){
		/*
		
		// get mapper class
		xxxMapper mapper = session.getMapper(xxxMapper.class);

		// domain
		xx domain = new xx();
		domain.setSourceFileName("test");
		domain.setSourceId(10);
		
		// mapper클래스의 메소드 호출
		mapper.createLogNws(domain);
		
		// 검증
		assertEquals(1, domain.getStatus());
		
		*/
		
	}
	
	@AfterClass
	public void tearDown(){
		session.close();
	}
}
