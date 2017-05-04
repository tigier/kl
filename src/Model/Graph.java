package Model;

/**
 * <p>
 * Materialien zu den zentralen NRW-Abiturpruefungen im Fach Informatik ab 2018
 * </p>
 * <p>
 * Klasse Graph
 * </p>
 * <p>
 * Die Klasse Graph stellt einen ungerichteten, kantengewichteten Graphen dar. Es koennen 
 * Knoten- und Kantenobjekte hinzugefuegt und entfernt, flache Kopien der Knoten- und Kantenlisten 
 * des Graphen angefragt und Markierungen von Knoten und Kanten gesetzt und ueberprueft werden.
 * Des Weiteren kann eine Liste der Nachbarn eines bestimmten Knoten, eine Liste der inzidenten 
 * Kanten eines bestimmten Knoten und die Kante von einem bestimmten Knoten zu einem 
 * anderen bestimmten Knoten angefragt werden. Abgesehen davon kann abgefragt werden, welches 
 * Knotenobjekt zu einer bestimmten ID gehoert und ob der Graph leer ist.
 * </p>
 * 
 * @author Qualitaets- und UnterstuetzungsAgentur - Landesinstitut fuer Schule
 * @version Oktober 2015
 */
public class Graph{
  private List<Vertex> vertices;
  private List<Edge> edges;

  /**
   * Ein Objekt vom Typ Graph wird erstellt. Der von diesem Objekt 
   * repraesentierte Graph ist leer.
   */
  public Graph(){
    //Leere Listen fuer Knoten und Kanten erstellen.
    vertices = new List<Vertex>();
    edges = new List<Edge>();
  }

  /**
   * Die Anfrage liefert eine neue Liste aller Knotenobjekte vom Typ List<Vertex>.
   * Nachdem die Ergebnisliste fertiggestellt wurde, wird ihr erstes Element das aktuelle Element innerhalb dieser Liste.
   */
  public List<Vertex> getVertices(){
    //Eine neue Liste mit allen Vertex-Objekten erstellen.
    List<Vertex> result = new List<Vertex>();

    vertices.toFirst();
    while(vertices.hasAccess()){
      result.append(vertices.getContent());
      vertices.next();
    }
    //TODO 01: Einmal Liste kopieren, bitte! Warum eigentlich kopieren? (O_o)(done)

    //Aktuelles Element zum Anfang bewegen.
    result.toFirst();

    return result;
  }

  /**
   * Die Anfrage liefert eine neue Liste aller Kantenobjekte vom Typ List<Edge>.
   * Nachdem die Ergebnisliste fertiggestellt wurde, wird ihr erstes Element das aktuelle Element innerhalb dieser Liste.
   */
  public List<Edge> getEdges(){
    //Eine neue Liste mit allen Edge-Objekten erstellen.
    List<Edge> result = new List<Edge>();

    edges.toFirst();
    while (edges.hasAccess()){
      result.append(edges.getContent());
      edges.next();
    }
    //TODO 02: Und nochmal kopieren.(done)

    //Aktuelles Element zum Anfang bewegen.
    result.toFirst();

    return result;
  }

  /**
   * Die Anfrage liefert das Knotenobjekt mit pID als ID. Ist ein solches Knotenobjekt nicht im Graphen enthalten,
   * wird null zurueckgeliefert.
   */
  public Vertex getVertex(String pID){

      if(pID != null) {
          List<Vertex> help = getVertices();
          help.toFirst();
          while (help.hasAccess()){
              if(pID.equals(help.getContent().getID())){
                  return help.getContent();
              }
              help.next();
          }

      }
    //TODO 03: Knoten-Objekt finden.(done)

    return null;
  }

  /**
   * Der Auftrag fuegt den Knoten pVertex in den Graphen ein, sofern es noch keinen
   * Knoten mit demselben ID-Eintrag wie pVertex im Graphen gibt und pVertex eine ID ungleich null hat. 
   * Ansonsten passiert nichts.
   */
  public boolean addVertex(Vertex pVertex){
      if(getVertex(pVertex.getID()) == null && pVertex.getID() != null){
          vertices.append(pVertex);
          return true;
      }
      return false;
    //TODO 04: Neues Knoten-Objekt hinzufügen.(done)
  }

  /**
   * Der Auftrag fuegt die Kante pEdge in den Graphen ein, sofern beide durch die Kante verbundenen Knoten
   * im Graphen enthalten sind, nicht identisch sind und noch keine Kante zwischen den Knoten existiert. Ansonsten passiert nichts.
   */
  public boolean addEdge(Edge pEdge){
    //Pruefen, ob pEdge exisitert.
    if (pEdge != null){  
      Vertex[] vertexPair = pEdge.getVertices();
      
      //Einfuegekriterien pruefen.
      if (vertexPair[0] != null && vertexPair[1] != null && 
      this.getVertex(vertexPair[0].getID()) == vertexPair[0] && 
      this.getVertex(vertexPair[1].getID()) == vertexPair[1] &&
      this.getEdge(vertexPair[0], vertexPair[1]) == null && 
      vertexPair[0] != vertexPair[1]){
        //Kante einfuegen.
        edges.append(pEdge);
        return true;
      }
    }
    return false;
  }

  /**
   * Der Auftrag entfernt den Knoten pVertex aus dem Graphen und loescht alle Kanten, die mit ihm inzident sind.
   * Ist der Knoten pVertex nicht im Graphen enthalten, passiert nichts.
   */
  public boolean removeVertex(Vertex pVertex){
    //Inzidente Kanten entfernen.
    edges.toFirst();
    while (edges.hasAccess()){
      Vertex[] akt = edges.getContent().getVertices();
      if (akt[0] == pVertex || akt[1] == pVertex){
        edges.remove();
      } else {
        edges.next();
      }
    }

    //Knoten entfernen
    vertices.toFirst();
    while (vertices.hasAccess() && vertices.getContent()!= pVertex){
      vertices.next();
    }
    if (vertices.hasAccess()){
      vertices.remove();
      return true;
    }
    return false;
  }

  /**
   * Der Auftrag entfernt die Kante pEdge aus dem Graphen. Ist die Kante pEdge nicht 
   * im Graphen enthalten, passiert nichts.
   */
  public boolean removeEdge(Edge pEdge){
    //Kante aus Kantenliste des Graphen entfernen.
    edges.toFirst();
    while (edges.hasAccess()){
      if (edges.getContent() == pEdge){
        edges.remove();
        return true;
      } else {
        edges.next();
      }
    }
    return false;
  }

  /**
   * Der Auftrag setzt die Markierungen aller Knoten des Graphen auf pMark.
   */
  public void setAllVertexMarks(boolean pMark){
    vertices.toFirst();
    while (vertices.hasAccess()){
      vertices.getContent().setMark(pMark);
      vertices.next();
    }
  }

  /**
   * Der Auftrag setzt die Markierungen aller Kanten des Graphen auf pMark.
   */
  public void setAllEdgeMarks(boolean pMark){
    edges.toFirst();
    while (edges.hasAccess()){
      edges.getContent().setMark(pMark);
      edges.next();
    }
  }

  /**
   * Die Anfrage liefert true, wenn alle Knoten des Graphen mit true markiert sind, ansonsten false.
   */
  public boolean allVerticesMarked(){
    boolean result = true;
    vertices.toFirst();
    while (vertices.hasAccess()){
      if (!vertices.getContent().isMarked()){
        result = false;
      }
      vertices.next();
    }
    return result;
  }

  /**
   * Die Anfrage liefert true, wenn alle Kanten des Graphen mit true markiert sind, ansonsten false.
   */
  public boolean allEdgesMarked(){
    boolean result = true;
    edges.toFirst();
    while (edges.hasAccess()){
      if (!edges.getContent().isMarked()){
        result = false;
      }
      edges.next();
    }
    return result;
  }

  /**
   * Die Anfrage liefert alle Nachbarn des Knotens pVertex als neue Liste vom Typ List<Vertex>. Hat der Knoten
   * pVertex keine Nachbarn in diesem Graphen oder ist gar nicht in diesem Graphen enthalten, so 
   * wird eine leere Liste zurueckgeliefert.
   */
  public List<Vertex> getNeighbours(Vertex pVertex){
    List<Vertex> result = new List<Vertex>();
    
    //Alle Kanten durchlaufen.
    edges.toFirst();
    while (edges.hasAccess()){
      
      //Wenn ein Knoten der Kante pVertex ist, den anderen als Nachbarn in die Ergebnisliste einfuegen.
      Vertex[] vertexPair = edges.getContent().getVertices();
      if (vertexPair[0] == pVertex) {
        result.append(vertexPair[1]);
      } else { 
        if (vertexPair[1] == pVertex){
          result.append(vertexPair[0]);
        }
      }
      edges.next();
    }    
    return result;
  }

  public Vertex getNeighbourAt(Vertex vertex, int index){
      List<Vertex> help = getNeighbours(vertex);
      Vertex out = null;
      help.toFirst();
      if(help.hasAccess()){
          for(int i = 0; i <= index; i++){
              out = help.getContent();
              help.next();
          }
          return out;
      }
      return null;
  }

  /**
   * Die Anfrage liefert eine neue Liste alle inzidenten Kanten zum Knoten pVertex. Hat der Knoten
   * pVertex keine inzidenten Kanten in diesem Graphen oder ist gar nicht in diesem Graphen enthalten, so 
   * wird eine leere Liste zurueckgeliefert.
   */
  public List<Edge> getEdges(Vertex pVertex){
    List<Edge> result = new List<Edge>();
    
    //Alle Kanten durchlaufen.
    edges.toFirst();
    while (edges.hasAccess()){
      
      //Wenn ein Knoten der Kante pVertex ist, dann Kante als inzidente Kante in die Ergebnisliste einfuegen.
      Vertex[] vertexPair = edges.getContent().getVertices();
      if (vertexPair[0] == pVertex) {
        result.append(edges.getContent());
      } else{ 
        if (vertexPair[1] == pVertex){
          result.append(edges.getContent());
        }
      }
      edges.next();
    }    
    return result;
  }

  /**
   * Die Anfrage liefert die Kante, welche die Knoten pVertex und pAnotherVertex verbindet, 
   * als Objekt vom Typ Edge. Ist der Knoten pVertex oder der Knoten pAnotherVertex nicht 
   * im Graphen enthalten oder gibt es keine Kante, die beide Knoten verbindet, so wird null 
   * zurueckgeliefert.
   */
  public Edge getEdge(Vertex pVertex, Vertex pAnotherVertex){
    Edge result = null;
    
    //Kanten durchsuchen, solange keine passende gefunden wurde.
    edges.toFirst();
    while (edges.hasAccess() && result == null){
      
      //Pruefen, ob die Kante pVertex und pAnotherVertex verbindet.
      Vertex[] vertexPair = edges.getContent().getVertices();
      if ((vertexPair[0] == pVertex && vertexPair[1] == pAnotherVertex) ||
      (vertexPair[0] == pAnotherVertex && vertexPair[1] == pVertex)) {
        //Kante als Ergebnis merken.
        result = edges.getContent();
      } 
      edges.next();
    }    
    return result;
  }

  /**
   * Die Anfrage liefert true, wenn der Graph keine Knoten enthaelt, ansonsten false.
   */
  public boolean isEmpty(){
    return vertices.isEmpty();
  }

  public int amountOfNeighbours(Vertex vertex){
    List<Vertex> help = getNeighbours(vertex);

    int i = 0;
      help.toFirst();
      while(help.hasAccess()){
          i++;
          help.next();
      }
      return i;
  }

}