package com.example.demo.Configuration;


import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration//注解到spring容器中
@MapperScan(basePackages = "com.example.demo.dao.mayubin",sqlSessionFactoryRef = "data1SqlSessionFactory")
public class DataSourceForMayubin {
    @Value("${spring.datasource.mayubin.jdbc-url}")
    private String dbUrl;
    @Value("${spring.datasource.mayubin.username}")
    private String username;
    @Value("${spring.datasource.mayubin.password}")
    private String password;
    @Value("${spring.datasource.mayubin.driver-class-name}")
    private String driverClassName;


    /**
     * 返回data1数据库的数据源
     * @return
     */
    @Bean(name="data1Source")
    @Primary//主数据源
//    @ConfigurationProperties(prefix = "spring.datasource.mayubin")
    public DataSource dataSource() throws  Exception{
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        // 基础连接信息
        druidXADataSource.setUrl(this.dbUrl);
        druidXADataSource.setUsername(username);
        druidXADataSource.setPassword(password);
        druidXADataSource.setDriverClassName(driverClassName);

        // 连接池连接信息
//        druidXADataSource.setInitialSize(initialSize);
//        druidXADataSource.setMinIdle(minIdle);
//        druidXADataSource.setMaxActive(maxActive);
//        druidXADataSource.setMaxWait(maxWait);
        druidXADataSource.setPoolPreparedStatements(true); // 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
        druidXADataSource.setMaxPoolPreparedStatementPerConnectionSize(50);
        druidXADataSource.setConnectionProperties("oracle.net.CONNECT_TIMEOUT=6000;oracle.jdbc.ReadTimeout=60000");// 对于耗时长的查询sql，会受限于ReadTimeout的控制，单位毫秒
        druidXADataSource.setTestOnBorrow(true); // 申请连接时执行validationQuery检测连接是否有效，这里建议配置为TRUE，防止取到的连接不可用
        druidXADataSource.setTestWhileIdle(true);// 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        String validationQuery = "select 1 from dual";
        druidXADataSource.setValidationQuery(validationQuery); // 用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
        druidXADataSource.setFilters("stat,wall");// 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
        druidXADataSource.setTimeBetweenEvictionRunsMillis(60000); // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidXADataSource.setMinEvictableIdleTimeMillis(180000); // 配置一个连接在池中最小生存的时间，单位是毫秒，这里配置为3分钟180000
        druidXADataSource.setKeepAlive(true); // 打开druid.keepAlive之后，当连接池空闲时，池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作，即执行druid.validationQuery指定的查询SQL，一般为select
        // * from
        // dual，只要minEvictableIdleTimeMillis设置的小于防火墙切断连接时间，就可以保证当连接空闲时自动做保活检测，不会被防火墙切断

        druidXADataSource.setRemoveAbandoned(true); // 是否移除泄露的连接/超过时间限制是否回收。
        druidXADataSource.setRemoveAbandonedTimeout(3600); // 泄露连接的定义时间(要超过最大事务的处理时间)；单位为秒。这里配置为1小时
        druidXADataSource.setLogAbandoned(true); //// 移除泄露连接发生是是否记录日志

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(druidXADataSource);
        xaDataSource.setUniqueResourceName("data1Source");
        return xaDataSource;
    }

    /**
     * 返回data1数据库的会话工厂
     * @param ds
     * @return
     * @throws Exception
     */
    @Bean(name = "data1SqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("data1Source") DataSource ds) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(ds);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:/mybatis/mayubin/*.xml"));
        return bean.getObject();
    }

    /**
     * 返回data1数据库的会话模板
     * @param sessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "data1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("data1SqlSessionFactory") SqlSessionFactory sessionFactory) throws  Exception{
        return  new SqlSessionTemplate(sessionFactory);
    }

    /**
     * 返回data1数据库的事务
     * @param ds
     * @return
     */
//    @Bean(name = "data1TransactionManager")
//    @Primary
//    public DataSourceTransactionManager transactionManager(@Qualifier("data1Source") DataSource ds){
//        return new DataSourceTransactionManager(ds);
//    }
}