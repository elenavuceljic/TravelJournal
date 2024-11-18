INSERT INTO journal_entry (id, title, description, entryDate, userId) VALUES (1, 'Iceland', 'Aurora Borealis hunting', '1995-09-12T00:00:00Z', 'auth0|671b7f2569de9ee1174865cd');
INSERT INTO journal_entry (id, title, description, entryDate, userId) VALUES (2, 'Ireland', 'Cliff hanging', '1995-09-11T00:00:00Z', 'auth0|671b7f573cb8c3f9176453af');
INSERT INTO journal_entry (id, title, description, entryDate, userId) VALUES (3, 'Portugal', 'Wave catching', '1995-09-10T00:00:00Z', 'auth0|671b7f573cb8c3f9176453af');
ALTER SEQUENCE journal_entry_id_seq RESTART WITH 4;