-- Keyspace definition
CREATE KEYSPACE play WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

-- Table definition
CREATE TABLE quote (
  id uuid,
  state text,
  application_id text,
  entity_id text,
  tx_date timestamp,
  message text,
  PRIMARY KEY(id, tx_date, state)
) WITH CLUSTERING ORDER BY (tx_date DESC, state ASC);

-- Solr Core setup
bin/dsetool create_core play.quote generateResources=true reindex=true
bin/dsetool get_core_schema play.quote
bin/dsetool get_core_config play.quote current=true