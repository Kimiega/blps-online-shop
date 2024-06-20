package com.kimiega.onlineshop.datamapper.usermanagement

import com.atomikos.jdbc.AtomikosDataSourceBean
import com.kimiega.onlineshop.config.Database1
import jakarta.persistence.EntityManagerFactory
import org.postgresql.xa.PGXADataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*


@Configuration
@EnableJpaRepositories(
    basePackages = ["com.kimiega.onlineshop.repository.usermanagement"],
    entityManagerFactoryRef = "userManagementEntityManager",
    transactionManagerRef = "transactionManager",
)
class UserManagementConfig(
    private val dbProperties: Database1,
) {
    @Bean
    fun userManagementDatabase(): PGXADataSource {
        return PGXADataSource().apply {
            user = dbProperties.username
            password = dbProperties.password
            setURL(dbProperties.url)
        }
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    fun userManagementDataSource(): AtomikosDataSourceBean {
        val dataSource = AtomikosDataSourceBean()
        dataSource.uniqueResourceName = "dbUserManagement"
        dataSource.xaDataSource = userManagementDatabase()
        dataSource.setPoolSize(10)

        return dataSource
    }

    @Bean
    fun userManagementEntityManager(): EntityManagerFactory? {
        val vendorAdapter = HibernateJpaVendorAdapter()
        val factory = LocalContainerEntityManagerFactoryBean()
        factory.jpaVendorAdapter = vendorAdapter
        factory.setPackagesToScan("com.kimiega.onlineshop.datamapper.usermanagement")
        factory.dataSource = userManagementDataSource()
        val jpaProperties = Properties()
        jpaProperties["generate-ddl"] = "true"
        jpaProperties["show_sql"] = "true"
        jpaProperties["hibernate.ddl_auto"] = "update"
        jpaProperties["database"] = "postgresql"
        jpaProperties["hibernate.current_session_context_class"] = "jta"
        jpaProperties["jakarta.persistence.transactionType"] = "jta"
        jpaProperties["hibernate.transaction.manager_lookup_class"] =
            "com.atomikos.icatch.jta.hibernate3.TransactionManagerLookup"
        jpaProperties["hibernate.hbm2ddl.auto"] = "update"
        factory.setJpaProperties(jpaProperties)
        factory.afterPropertiesSet()
        return factory.`object`
    }
}
