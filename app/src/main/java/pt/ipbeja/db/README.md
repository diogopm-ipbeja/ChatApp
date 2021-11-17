# Base de dados

Defina aqui os vários componentes necessários para uma implementação do Room:
- ChatDatabase (subclasse de `RoomDatabase`)
- ContactDao
  - Deve ser possível realizar as várias operações CRUD
- Contact (Entity) com atributos:
  - id: Long como Primary Key
  - name: String
  - outros que ache necessários (ver `CreateContactFragment`)
