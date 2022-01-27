#Build into local image named payara/cluster-demo
#FROM payara/micro:5.2021.10
FROM nexus.payara.fish:5000/payara/micro:5.35.0

#Install Linux Utilities (for lscpu)
USER root
RUN apk add util-linux vim && \
    chown root:payara /usr/bin/lscpu

USER payara
#COPY --chown=payara:payara logging.properties /opt/payara/log-config/logging.properties
COPY target/cluster-demo.war /opt/payara/deployments/cluster-demo.war