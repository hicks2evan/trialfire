package hicks2evan;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("hicks2evan");

        noClasses()
            .that()
                .resideInAnyPackage("hicks2evan.service..")
            .or()
                .resideInAnyPackage("hicks2evan.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..hicks2evan.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
