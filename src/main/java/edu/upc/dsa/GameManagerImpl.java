package edu.upc.dsa;

import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GameManagerImpl implements GameManager {

    //llamamos al fichero de propiedades log4j
    final static Logger logger = Logger.getLogger(GameManagerImpl.class);

    //implementamos la fachada como Singleton
    private static GameManager instance;

    //inicializamos la matriz de usuarios
    private HashMap<String, User> users;

    //a parte de añadir el hash/tabla de usuarios
    //vamos a agregar todas las listas de las diferentes clases
    //listas de usuario
    private ArrayList<Play> playsByPlayer;
    private ArrayList<Item> itemsByPlayer;
    //agregamos números de los partidas iniciales e items iniciales
    //y los inicializamos en el constructor
    private int numPlays, numItems, numUsers, numMaps;

    //lista de partida
    private LinkedList<Map> mapsByPlay;

    //listas de mapa
    private LinkedList<Enemy> enemiesByMap;
    private LinkedList<Ally> alliesByMap;

    //constructor privado vacio de momento
    private GameManagerImpl() {

        //inicializamos número de items y partidas
        numPlays = 0;
        numItems = 0;
        numUsers = 0;
        numMaps = 0;
        //inicializamos en el constructor también las diferentes listas
        playsByPlayer = new ArrayList<>();
        itemsByPlayer = new ArrayList<>();
        mapsByPlay = new LinkedList<>();
        enemiesByMap = new LinkedList<>();
        alliesByMap = new LinkedList<>();
        users = new HashMap<>();

    }

    //aqui implementamos el singleton
    public static GameManager getInstance() {
        if (instance==null) instance = new GameManagerImpl();
        return instance;
    }

    //limpiar todas las estructuras de datos
    @Override
    public void clear() {
        instance=null;
        //linkedlist y arraylist funcionan de la misma manera
        playsByPlayer.clear();
        itemsByPlayer.clear();
        mapsByPlay.clear();
        enemiesByMap.clear();
        alliesByMap.clear();
        users.clear();
    }

    //funcion que implementa el contrato UserManager
    //función añadir usuario
    //Add a new User
    public void addUser(String id, String username, String password){
        if (this.users.containsKey(id)){

        }
        else{
            this.users.put(id, new User(id, username, password));
        }
    }

    @Override
    public int numUsers() {
        logger.info("Numero de usuarios en el sistema: " +this.users.size());
        return this.users.size();
    }

    @Override
    public void addPlay(String id, String positionX, String positionY) throws UserNotFoundException {

    }

    //user es player
    @Override
    public void addItem(String id, String name, String total, String idPlayer) throws UserNotFoundException {
//        User user = null;
//        Item item;
//        for(int i = 0; i<this.numUsers;i++){
//            if(idPlayer.equals(this.users[i].idPlayer)){
//                user = this.users[i];
//            }
//        }
    }

    @Override
    public void addMap(String id, String level, String total) {

    }

    @Override
    public void addEnemy(String id, String name, String total, String idMap) throws MapNotFoundException, MapFullException{
        Map map = null;
        for(int i = 0; i<this.numMaps; i++ ){
            if(idMap.equals(this.mapsByPlay.get(i).getId())){
                map = this.mapsByPlay.get(i);
            }
        }
        logger.info("Map: " + map);

        if(map != null){ //si el mapa no es nulo, si hay un mapa
            LinkedList<Enemy> enemies = (LinkedList<Enemy>) map.getEnemiesByMap();
            int maxenemies = Integer.parseInt(map.getTotal());
            if (enemies.size()<maxenemies){
                map.addEnemy(new Enemy(id, name, total, idMap));
            }
            else{
                logger.error("El mapa en el que quiere añadir un enemigo está lleno.");
                throw new MapFullException();
            }
        }
        else{
            logger.error("El mapa no existe.");
            throw new MapNotFoundException();
        }
    }

    @Override
    public void addAlly(String id, String name, String total, String idMap) throws MapNotFoundException, MapFullException{
        Map map = null;
        for(int i = 0; i<this.numMaps; i++ ){
            if(idMap.equals(this.mapsByPlay.get(i).getId())){
                map = this.mapsByPlay.get(i);
            }
        }
        logger.info("Map: " + map);

        if(map != null){ //si el mapa no es nulo, si hay un mapa
            LinkedList<Ally> allies = (LinkedList<Ally>) map.getAlliesByMap();
            int maxallies = Integer.parseInt(map.getTotal());
            if (allies.size()<maxallies){
                map.addAlly(new Ally(id, name, total, idMap));
            }
            else{
                logger.error("El mapa en el que quiere añadir un aliado está lleno.");
                throw new MapFullException();
            }
        }
        else{
            logger.error("El mapa no existe.");
            throw new MapNotFoundException();
        }
    }

    @Override
    public void saveStatus(String idPlay, String username) {

    }

    @Override
    public int getStatus(String idPlay, String username) throws PlayNotFoundException {
        return 0;
    }

    //a continuación a implementar todas las funciones creadas
    @Override
    public User getUser(String id) throws UserNotFoundException { //devuelve el usuario según id
        User user = this.users.get(id);
        return user;
    }

    @Override
    public User deleteUser(String id, String password) throws UserNotFoundException {
        User user = null;
        //MODIFICAR QUE LA CONTRASEÑA
        if (this.users.containsKey(id) && user.getPassword().equals(password)){
            this.users.remove(id);
            logger.info("El usuario se ha eliminado correctamente.");
        }
        else{
            logger.info("Este usuario no existe.");
            throw new UserNotFoundException();
        }
        //
        return null;
    }

    @Override
    public User updateUser() throws UserNotFoundException {
        return null;
    }

}