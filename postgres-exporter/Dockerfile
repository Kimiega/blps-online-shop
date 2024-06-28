FROM prometheuscommunity/postgres-exporter

COPY postgres-exporter.yml /postgres-exporter.yml

EXPOSE 9187

CMD ["--config.file", "/postgres-exporter.yml"]