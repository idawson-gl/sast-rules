// Temporary type declaration to avoid lint errors on Typescript test files

declare module "neo4j-driver" {
  function session(): Session
}

interface Session {
  run(query: string, args?: any): Promise<any>
}
