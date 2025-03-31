
package com.cymbaleats.shopping.cart;



import org.springframework.context.annotation.Configuration;
import com.google.cloud.spring.data.spanner.core.convert.SpannerEntityProcessor;
import com.google.cloud.spring.data.spanner.core.mapping.SpannerMappingContext;
import com.google.cloud.spring.data.spanner.core.convert.ConverterAwareMappingSpannerEntityProcessor;
import com.google.cloud.spring.data.spanner.core.admin.SpannerSchemaUtils;
import com.google.cloud.spring.data.spanner.core.SpannerMutationFactory;
import com.google.cloud.spring.data.spanner.core.SpannerMutationFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.google.cloud.spring.data.spanner.core.SpannerTemplate;
import com.google.cloud.spanner.SpannerOptions;
import com.google.cloud.spanner.DatabaseId;
import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spring.data.spanner.core.admin.SpannerDatabaseAdminTemplate;
import com.google.cloud.spanner.DatabaseAdminClient;
import com.google.cloud.spring.core.DefaultCredentialsProvider;
import com.google.cloud.spring.autoconfigure.spanner.GcpSpannerProperties;
import java.io.IOException;
@Configuration
public class DataSourceConfiguration {

  private static  String instanceId;
  private static  String projectId;
  private static  String database;
  private static  String applicationName;
   static SpannerTemplate spannerTemplate;
   static SpannerDatabaseAdminTemplate spannerDatabaseAdminTemplate;
   static SpannerSchemaUtils spannerSchemaUtils;


  @Autowired
  public DataSourceConfiguration(@Value("${spring.cloud.gcp.spanner.instance-id}") String instanceId,@Value("${spring.cloud.gcp.spanner.database}") String database,
      @Value("${spring.cloud.gcp.spanner.project-id}") String projectId, @Value("${spring.application.name}") String applicationName) {
    this.database = database;
    this.instanceId = instanceId;
    this.projectId = projectId;
    this.applicationName = applicationName;
    System.out.println("test 3"+ instanceId);
  }

  @Bean
  public SpannerMappingContext spannerMappingContext() {
    return new SpannerMappingContext();
  }

  @Bean
  public SpannerEntityProcessor spannerEntityProcessor(SpannerMappingContext mappingContext) {
    return new ConverterAwareMappingSpannerEntityProcessor(mappingContext);
  }

  @Bean
  public SpannerSchemaUtils spannerSchemaUtils(
      SpannerMappingContext spannerMappingContext, SpannerEntityProcessor spannerEntityProcessor) {
    spannerSchemaUtils = new SpannerSchemaUtils(spannerMappingContext, spannerEntityProcessor, true);
    return spannerSchemaUtils;
  }

  @Bean
  public SpannerMutationFactory spannerMutationFactory(
      SpannerEntityProcessor spannerEntityProcessor,
      SpannerMappingContext spannerMappingContext,
      SpannerSchemaUtils spannerSchemaUtils) {
    return new SpannerMutationFactoryImpl(
        spannerEntityProcessor, spannerMappingContext, spannerSchemaUtils);
  }



  //Spanner Template for DB1
  @Bean
  @Qualifier("db1Template")
  public SpannerTemplate db1SpannerTemplate(SpannerMappingContext mappingContext,
      SpannerEntityProcessor spannerEntityProcessor,
      SpannerSchemaUtils spannerSchemaUtils,
      SpannerMutationFactory spannerMutationFactory) throws IOException {
      SpannerOptions bagSpannerOptions = SpannerOptions.newBuilder().setProjectId(projectId).
          setCredentials(new DefaultCredentialsProvider(new GcpSpannerProperties()).getCredentials()).build();

    DatabaseId databaseId = DatabaseId.of(projectId,
        instanceId,
        database);

    DatabaseClient databaseClient = bagSpannerOptions.getService().getDatabaseClient(databaseId);
    System.out.println(databaseClient+" I am in database");
    spannerTemplate =new SpannerTemplate(
        () -> databaseClient,
        mappingContext,
        spannerEntityProcessor,
        spannerMutationFactory,
        spannerSchemaUtils);
    return spannerTemplate;
  }

  @Bean
  @Qualifier("db1TemplateAdmin")
  public SpannerDatabaseAdminTemplate db1SpannerAdminTemplate(SpannerMappingContext mappingContext,
      SpannerEntityProcessor spannerEntityProcessor,
      SpannerSchemaUtils spannerSchemaUtils,
      SpannerMutationFactory spannerMutationFactory) {
    SpannerOptions bagSpannerOptions = SpannerOptions.newBuilder().setProjectId(projectId).build();

    DatabaseId databaseId = DatabaseId.of(projectId,
        instanceId,
        database);

    DatabaseAdminClient databaseAdminClient =bagSpannerOptions.getService().getDatabaseAdminClient();
    DatabaseClient databaseClient = bagSpannerOptions.getService().getDatabaseClient(databaseId);
    spannerDatabaseAdminTemplate = new SpannerDatabaseAdminTemplate(
        databaseAdminClient,
        ()-> databaseClient,
        ()-> databaseId);
    System.out.println(spannerDatabaseAdminTemplate+" I am in database");
    return spannerDatabaseAdminTemplate;
  }
}