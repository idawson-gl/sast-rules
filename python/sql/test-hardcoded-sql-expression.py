# License: Apache 2.0 (c) PyCQA
# source: https://github.com/PyCQA/bandit/blob/master/examples/sql_statements.py
# hash:  8eee173

import sqlalchemy

# bad
cur.execute("SELECT * FROM foo WHERE id = '%s'" % identifier)
cur.execute("INSERT INTO foo VALUES ('a', 'b', '%s')" % value)
cur.execute("DELETE FROM foo WHERE id = '%s'" % identifier)
cur.execute("UPDATE foo SET value = 'b' WHERE id = '%s'" % identifier)
# bad alternate forms
cur.execute("SELECT * FROM foo WHERE id = '" + identifier + "'")
cur.execute("SELECT * FROM foo WHERE id = '{}'".format(identifier))

# good
cur.execute("SELECT * FROM foo WHERE id = '%s'", identifier)
cur.execute("INSERT INTO foo VALUES ('a', 'b', '%s')", value)
cur.execute("DELETE FROM foo WHERE id = '%s'", identifier)
cur.execute("UPDATE foo SET value = 'b' WHERE id = '%s'", identifier)

# real world false positives
choices=[('server_list', _("Select from active instances"))]
print("delete from the cache as the first argument")

def select_note_by_id(conn, id=None):
	query = "SELECT * FROM notes"
	if id:
		query = query + " WHERE id = '%s'" % id
	cur = conn.cursor()
	cur.execute(query)
	rows = cur.fetchall()
	return rows
