// License: MIT (c) GitLab Inc.
// hash: edd1ae2

"use strict";

const neo4jClientVariant1 = require("neo4j-driver");
const neo4jClientVariant2 = require("neo4j-driver").v1;
import neo4jClientVariant3 from "neo4j-driver";

async function danger(email) {
  var session1 = neo4jClientVariant1.session();
  var query =
    "MATCH (user) WHERE user.email = '" + email + "' RETURN user IS NOT NULL;";
  session1
    .run(query) // DANGER
    .then(function (result) {
      console.log(result);
    })
    .catch(function (error) {
      console.log(error);
    });

  var session2 = neo4jClientVariant2.session();
  var query =
    "MATCH (user) WHERE user.email = '" + email + "' RETURN user IS NOT NULL;";
  try {
    const result = await session2.run(query); // DANGER
    console.log(result);
  } catch (error) {
    console.log(error);
  }

  var session3 = neo4jClientVariant3.session();
  var query = buildQuery(email);
  try {
    const result = await session3.run(query); // DANGER
    console.log(result);
  } catch (error) {
    console.log(error);
  }

  var session4 = neo4jClientVariant2.session();
  let modifiedEmail = email + ".com";
  var query =
    "MATCH (user) WHERE user.email = '" +
    modifiedEmail +
    "' RETURN user IS NOT NULL;";
  try {
    const result = await session4.run(query); // DANGER
    console.log(result);
  } catch (error) {
    console.log(error);
  }
}

function buildQuery(email) {
  return (
    "MATCH (user) WHERE user.email = '" + email + "' RETURN user IS NOT NULL;"
  );
}

async function ok(email) {
  var session3 = neo4jClientVariant3.session();
  var time = new Date().getTime();
  var query =
    "MATCH (user) WHERE user.date_of_birth >= '" +
    time +
    "' RETURN user IS NOT NULL;";
  try {
    const result = await session3.run(query); // SAFE
    console.log(result);
  } catch (error) {
    console.log(error);
  }

  var session1 = neo4jClientVariant1.session();
  var time = new Date().getTime();
  var emailLocal = "test@test.com";
  var query =
    "MATCH (user) WHERE user.email = '" +
    emailLocal +
    "' AND user.date_of_birth = '" +
    time +
    "' RETURN user IS NOT NULL;";
  session1
    .run(query) // DANGER
    .then(function (result) {
      console.log(result);
    })
    .catch(function (error) {
      console.log(error);
    });
}

// GQL SCENARIOS

const graphQLResolvers = {
  Query: {
    // SAFE QUERY
    SafeQuery: (_, __, context) => {
      let session = context.neo4jClient.session();
      let query = "MATCH (u:User) RETURN u, id(u)";
      return session
        .run(query)
        .then((result) => {
          console.log(result);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // DANGER QUERY
    DangerQuery: (_, args, context) => {
      let session = context.neo4jClient.session();
      let query = "MATCH (u:User) ";
      query += "OPTIONAL MATCH (a:TestApp) WHERE (a)--(d)";
      query += "RETURN d, id(d), count(a) ORDER BY d." + args["orderBy"]; // DANGER
      return session
        .run(query)
        .then((result) => {
          console.log(result);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
  Mutation: {
    // DANGER MUTATION
    dangerMutation: (_, args, context) => {
      let session = context.neo4jClient.session();
      let query = "CREATE (a:TestApp{";
      for (var key in args) {
        if (key != "test") {
          query += key + ": $" + key + ", "; // DANGER
        }
      }
      query += "test1: ";
      query += new Date().getTime();
      query += ", test2: ";
      query += "}) RETURN a, id(a)";
      return session
        .run(query, args)
        .then((result) => {
          console.log(result);
        })
        .catch(function (error) {
          console.error(error);
        });
    },
    // SAFE MUTATION
    safeMutation: (_, args, context) => {
      let session = context.neo4jClient.session();
      let query = "CREATE (a:Application{";
      let params = ["test1", "test2"];
      for (var key in params) {
        if (key != "test") {
          query += key + ": $" + key + ", ";
        }
      }
      query += "test1: ";
      query += new Date().getTime();
      query += ", test2: ";
      query += "}) RETURN a, id(a)";
      return session
        .run(query, args)
        .then((result) => {
          console.log(result);
        })
        .catch(function (error) {
          console.error(error);
        });
    },
  },
};
