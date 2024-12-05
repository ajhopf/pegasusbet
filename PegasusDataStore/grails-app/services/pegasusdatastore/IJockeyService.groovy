package pegasusdatastore

interface IJockeyService {
    List<Jockey> list(Map args)
    Jockey save(Jockey jockey)
    Jockey getJockey(Serializable id)
    boolean deleteJockey(Serializable id)
    Number count()
}