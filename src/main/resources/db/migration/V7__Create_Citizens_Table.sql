CREATE TABLE citizens(
    id SERIAL PRIMARY KEY,
    fullName TEXT,
    house_id INT REFERENCES houses(id),
    workplace_id INT REFERENCES workplaces(id)
    );