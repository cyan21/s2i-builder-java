# my-builder
FROM openshift/base-centos7

# TODO: Put the maintainer name in the image metadata
LABEL maintainer="yann.chaysinh@gmail.com"

# TODO: Rename the builder environment variable to inform users about application you provide them
ARG JAVA_VERSION="1.8-131"
ARG MAVEN_VERSION="3.6.2"

ENV BUILDER_VERSION 0.0.1
ENV M2_HOME="/usr/local/apache-maven-${MAVEN_VERSION}"

ENV PATH="${PATH}:${M2_HOME}/bin"

# TODO: Set labels used in OpenShift to describe the builder image
LABEL io.k8s.description="Platform for building springboot apps" \
      io.k8s.display-name="builder 0.0.1" \
      io.openshift.expose-services="8080:http" \
      io.openshift.tags="builder,openjdk-0.0.1,"

RUN yum install -y java-1.8.0-openjdk-devel &&  \
    yum clean all -y

# installing JFrog CLI
RUN curl -fL https://getcli.jfrog.io | sh && \
    chmod 755 jfrog && \
    mv jfrog /usr/local/bin/

RUN curl "http://mirrors.whoishostingthis.com/apache/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz" | tar -xz -C /usr/local/ && \
    chown -R root:root /usr/local/apache-maven-${MAVEN_VERSION}

#RUN yum install -y rubygems && yum clean all -y
#RUN gem install asdf

# TODO (optional): Copy the builder files into /opt/app-root
# COPY ./<builder_folder>/ /opt/app-root/

# TODO: Copy the S2I scripts to /usr/libexec/s2i, since openshift/base-centos7 image
# sets io.openshift.s2i.scripts-url label that way, or update that label
COPY ./s2i/bin/ /usr/libexec/s2i

RUN chmod -R +x /usr/libexec/s2i

# TODO: Drop the root user and make the content of /opt/app-root owned by user 1001
RUN chown -R 1001:1001 /opt/app-root

# This default user is created in the openshift/base-centos7 image
USER 1001

# TODO: Set the default port for applications built using this image
EXPOSE 8080

# TODO: Set the default CMD for the image
# CMD ["/usr/libexec/s2i/usage"]
#ENTRYPOINT ["mvn --version"]
CMD ["mvn --version && jfrog --version"]
