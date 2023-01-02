package org.bukkit.plugin.java;

import dev.jeka.core.api.depmanagement.JkDependencySet;
import dev.jeka.core.api.depmanagement.JkRepo;
import dev.jeka.core.api.depmanagement.resolution.JkDependencyResolver;
import dev.jeka.core.api.depmanagement.resolution.JkModuleDepProblem;
import dev.jeka.core.api.depmanagement.resolution.JkResolveResult;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibraryLoader {
    private final Logger logger;
    private final JkDependencyResolver<Void> resolver = JkDependencyResolver.of()
            .addRepos(JkRepo.ofMavenCentral())
            .addRepos(JkRepo.ofLocal())
            .addRepos(JkRepo.ofLocalIvy());

    public LibraryLoader(@NotNull final Logger logger) {
        this.logger = logger;
    }

    @Nullable
    public ClassLoader createLoader(@NotNull PluginDescriptionFile desc) {
        if (desc.getLibraries().isEmpty()) return null;
        this.logger.log(Level.INFO, "[{0}] Loading {1} libraries... please wait", new Object[]{desc.getName(), desc.getLibraries().size()});

        JkDependencySet dependencySet = JkDependencySet.of();
        for (String library : desc.getLibraries()) {
            dependencySet = dependencySet.and(library);
        }

        dependencySet = dependencySet.and("org.apache.commons:commons-lang3:3.12.0");

        JkResolveResult resolveResult = resolver.resolve(dependencySet);
        JkResolveResult.JkErrorReport report = resolveResult.getErrorReport();
        if (report.hasErrors()) {
            for(JkModuleDepProblem problem : report.getModuleProblems()) {
                this.logger.log(Level.SEVERE, "[{0}] Error loading library '{1}:{2}', error: {3}",
                        new Object[] {
                                desc.getName(),
                                problem.getModuleId().getGroupAndName(),
                                problem.getVersion().getValue(),
                                problem.getProblemText()
                });
            }

            throw new IllegalStateException("Unable to fetch libraries, there have been %s error(s)!"
                    .formatted(report.getModuleProblems().size()));
        }

        return new URLClassLoader(resolveResult.getFiles().toUrls());
    }
}
