// License: MIT (c) GitLab Inc.
// hash: edd1ae2

import neo4jClient from "neo4j-driver";

async function danger(email: string) {
  var session1 = neo4jClient.session();
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

  var session2 = neo4jClient.session();
  var query =
    "MATCH (user) WHERE user.email = '" + email + "' RETURN user IS NOT NULL;";
  try {
    const result = await session2.run(query); // DANGER
    console.log(result);
  } catch (error) {
    console.log(error);
  }

  var session3 = neo4jClient.session();
  var query = buildQuery(email);
  try {
    const result = await session3.run(query); // DANGER
    console.log(result);
  } catch (error) {
    console.log(error);
  }

  var session4 = neo4jClient.session();
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

function buildQuery(email: string) {
  return (
    "MATCH (user) WHERE user.email = '" + email + "' RETURN user IS NOT NULL;"
  );
}

async function ok1(email: string) {
  var session3 = neo4jClient.session();
  var time = new Date().getTime();
  console.log(email);
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

  var session1 = neo4jClient.session();
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

const graphQLResolvers = {
  Query: {
    DangerQuery: (_, args: any, context: any) => {
        let session = context.neo4jClient.session();
        let query = "MATCH (u:User) ";
        query += "OPTIONAL MATCH (a:TestApp) WHERE (a)--(d)";
        query += "RETURN d, id(d), count(a) ORDER BY d." + args["orderBy"];
        return session
        .run(query)
        .then(result => {
           console.log(result);
        })
        .catch(function (error) {
          console.log(error);
        });
      },
    SafeQuery: (_, __, context: any) => {
          let session = context.neo4jClient.session();
          let query = "MATCH (u:User) RETURN u, id(u)";
          return session
          .run(query)
          .then(result => {
            console.log(result);
          })
          .catch(function (error) {
            console.log(error);
          });
        },


  },
  Mutation: {
    dangerMutation: (_, args: any, context: any) => {
      let session = context.neo4jClient.session();
      let query = "CREATE (a:TestApp{";
      for (var key in args) {
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
    safeMutation: (_, args: any, context: any) => {
      let session = context.neo4jClient.session();
      let query = "CREATE (a:Application{";
      let params = ["test1", "test2"]
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
