#Build into local image named payara/cluster-demo

FROM payara/micro:5.193.1

#Install Linux Utilities (for lscpu)
USER root
RUN apk add util-linux && \
    chown root:payara /usr/bin/lscpu

USER payara
COPY target/cluster-demo.war /opt/payara/deployments/cluster-demo.war