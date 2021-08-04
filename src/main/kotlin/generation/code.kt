package generation

import org.jooq.meta.jaxb.*
import org.jooq.meta.jaxb.Target


fun main() {
  // https://github.com/pgjdbc/pgjdbc
  Configuration().withJdbc(
    Jdbc()
      .withDriver("org.postgresql.Driver")
      .withUrl("jdbc:" + System.getenv("connectionUri"))
  ).withGenerator(
    Generator().withDatabase(
      Database().withName("org.jooq.meta.postgres.PostgresDatabase")
        .withIncludes(".*")
    )
      .withGenerate(Generate())
      .withTarget(Target().withPackageName("generation").withDirectory("./struct"))

  )
}

