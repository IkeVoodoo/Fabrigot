package org.bukkit.plugin.java;

import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

public class LibraryLoader {
    private final Logger logger;
    /*private final RepositorySystem repository;
    private final DefaultRepositorySystemSession session;
    private final List<RemoteRepository> repositories;*/
    private final JK

    public LibraryLoader(@NotNull final Logger logger) {
        this.logger = logger;
        /*DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
        locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
        locator.addService(TransporterFactory.class, HttpTransporterFactory.class);
        this.repository = (RepositorySystem)locator.getService(RepositorySystem.class);
        this.session = MavenRepositorySystemUtils.newSession();
        this.session.setChecksumPolicy("fail");
        this.session.setLocalRepositoryManager(this.repository.newLocalRepositoryManager(this.session, new LocalRepository("libraries")));
        this.session.setTransferListener(new AbstractTransferListener() {
            public void transferStarted(@NotNull TransferEvent event) throws TransferCancelledException {
                logger.log(Level.INFO, "Downloading {0}", event.getResource().getRepositoryUrl() + event.getResource().getResourceName());
            }
        });
        this.session.setReadOnly();
        this.repositories = this.repository.newResolutionRepositories(this.session, Arrays.asList((new RemoteRepository.Builder("central", "default", "https://repo.maven.apache.org/maven2")).build()));
    */

    }

    @Nullable
    public ClassLoader createLoader(@NotNull PluginDescriptionFile desc) {
        /*if (desc.getLibraries().isEmpty()) {
            return null;
        } else {
            this.logger.log(Level.INFO, "[{0}] Loading {1} libraries... please wait", new Object[]{desc.getName(), desc.getLibraries().size()});
            List<Dependency> dependencies = new ArrayList();
            Iterator var4 = desc.getLibraries().iterator();

            while(var4.hasNext()) {
                String library = (String)var4.next();
                Artifact artifact = new DefaultArtifact(library);
                Dependency dependency = new Dependency(artifact, (String)null);
                dependencies.add(dependency);
            }

            DependencyResult result;
            try {
                result = this.repository.resolveDependencies(this.session, new DependencyRequest(new CollectRequest((Dependency)null, dependencies, this.repositories), (DependencyFilter)null));
            } catch (DependencyResolutionException var11) {
                throw new RuntimeException("Error resolving libraries", var11);
            }

            List<URL> jarFiles = new ArrayList();
            Iterator var16 = result.getArtifactResults().iterator();

            while(var16.hasNext()) {
                ArtifactResult artifact = (ArtifactResult)var16.next();
                File file = artifact.getArtifact().getFile();

                URL url;
                try {
                    url = file.toURI().toURL();
                } catch (MalformedURLException var10) {
                    throw new AssertionError(var10);
                }

                jarFiles.add(url);
                this.logger.log(Level.INFO, "[{0}] Loaded library {1}", new Object[]{desc.getName(), file});
            }

            URLClassLoader loader = new URLClassLoader((URL[])jarFiles.toArray(new URL[jarFiles.size()]));
            return loader;
        }*/
    }
}
