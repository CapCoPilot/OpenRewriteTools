package com.testdriven;

import org.jetbrains.annotations.NotNull;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Recipe;
import org.openrewrite.TreeVisitor;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.JavaTemplate;
import org.openrewrite.java.MethodMatcher;
import org.openrewrite.java.tree.J.MethodDeclaration;

public class ChangeMethodBodyRecipe extends Recipe {

    private final String signature;
    private final String methodBody;

    public ChangeMethodBodyRecipe(String signature, String methodBody) {
        this.signature = signature;
        this.methodBody = methodBody;
    }

    @Override
    public @NotNull String getDisplayName() {
        return "Change method body";
    }

    @Override
    public @NotNull String getDescription() {
        return "Changes the body of a method. Assumes method is emnpty.";
    }

    // OpenRewrite provides a managed environment in which it discovers, instantiates, and wires configuration into Recipes.
    // This recipe has no configuration and delegates to its visitor when it is run.
    @Override
    public @NotNull TreeVisitor<?, ExecutionContext> getVisitor() {
        return new JavaIsoVisitor<ExecutionContext>() {
            // Used to identify the method declaration that will be refactored
            private final MethodMatcher methodMatcher = new MethodMatcher(signature);

            // Template used to add statements to the method body
            private final JavaTemplate addStatementsTemplate = JavaTemplate.builder(
                            methodBody)
                    .contextSensitive()
                    .build();

            @Override
            public @NotNull MethodDeclaration visitMethodDeclaration(@NotNull MethodDeclaration methodDeclaration, @NotNull ExecutionContext executionContext) {
                System.out.println("cycyle: " + executionContext.getCycle());
                if ( ! methodMatcher.matches(methodDeclaration.getMethodType())) {
                    return methodDeclaration;
                }

                //only append once
                if( executionContext.getCycle() > 1 ) {
                    return methodDeclaration;
                }

                assert methodDeclaration.getBody() != null;

                //append method body
                return addStatementsTemplate.apply(updateCursor(methodDeclaration), methodDeclaration.getBody().getCoordinates().lastStatement());
            }
        };
    }
}