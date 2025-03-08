package com.testdriven;



import org.junit.jupiter.api.Test;
import org.openrewrite.test.RecipeSpec;
import org.openrewrite.test.RewriteTest;

import static org.openrewrite.java.Assertions.java;

public class MethodReplaceTest implements RewriteTest {
    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new ChangeMethodBodyRecipe("com.yourorg.Customer setCustomerInfo(String)","System.out.println(\"hello\");"));
    }

    @Test
    void expandsExpectedCustomerInfoMethod() {
        rewriteRun(
                java(
                        """
                            package com.yourorg;
        
                            import java.util.Date;
        
                            public class Customer {
                                private Date dateOfBirth;
                                private String firstName;
                                private String lastName;
                                
                                public void foo(String lastName) {}
        
                                public void setCustomerInfo(String lastName) {
                                }
                            }
                        """,
                        """
                            package com.yourorg;
        
                            import java.util.Date;
        
                            public class Customer {
                                private Date dateOfBirth;
                                private String firstName;
                                private String lastName;
                                
                                public void foo(String lastName) {}
        
                                public void setCustomerInfo(String lastName) {
                                    System.out.println("hello");
                                }
                            }
                        """
                )
        );
    }
}