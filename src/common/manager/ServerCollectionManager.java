package common.manager;

import common.auth.User;
import common.network.Request;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.Printer;
import common.validators.ModelValidator;
import server.dao.DAO;
import server.dao.MusicBandDaoImpl;
import server.manager.CreatorManager;
import server.model.MusicBand;
import server.parse.YamlReader;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Contains tools for operation MusicBand collection
 *
 * @author ilestegor
 */

public class ServerCollectionManager {
    private final LocalDateTime creationDate;
    private final LinkedList<MusicBand> musicBandLinkedList = new LinkedList<>();
    private final ModelValidator modelValidator = new ModelValidator();
    private MusicBandDaoImpl musicBandDao;
    private CreatorManager creatorManager;
    private final HashSet<Number> idContainer = new HashSet<>();
    private final Printer printer = new Printer();


    public ServerCollectionManager(MusicBandDaoImpl musicBandDao, CreatorManager creatorManager) {
        creationDate = LocalDateTime.now();
        this.musicBandDao = musicBandDao;
        this.creatorManager = creatorManager;
    }

    public ServerCollectionManager() {
        creationDate = LocalDateTime.now();
    }


    /**
     * Method for validation input values and adding to collection new objects from file
     *
     * @param musicBand
     */
    public synchronized boolean validateAndAddToCollection(MusicBand musicBand) {
        if (idContainer.contains(musicBand.getId())) {
            musicBandLinkedList.remove(musicBand);
            printer.printNextLine("Объект с id" + musicBand.getId() + " уже существует! Объект не добавлен в коллекцию");
            return false;
        }
        idContainer.add(musicBand.getId());
        if (modelValidator.validateId(musicBand.getId()) && modelValidator.validateName(musicBand.getName())
                && modelValidator.validateCoordinateX(musicBand.getCoordinates().getX())
                && modelValidator.validateCoordinateY(musicBand.getCoordinates().getY()) && modelValidator.validateCreationDate(musicBand.getCreationDate())
                && modelValidator.validateNumberOfParticipants(musicBand.getNumberOfParticipants()) && modelValidator.validateAlbumsCount(musicBand.getAlbumsCount())
                && modelValidator.validateEstablishmentDate(musicBand.getEstablishmentDate()) && modelValidator.validateMusicGenre(musicBand.getGenre()) && modelValidator.validateLabel(musicBand.getLabel())) {
            musicBandLinkedList.add(musicBand);
            return true;
        } else {
            printer.printNextLine("Объект с id " + musicBand.getId() + " имеет невалидные поля! Объект не загружен в коллекцию");
            return false;
        }
    }


    /**
     * Validates and reads to musicBand to collection
     */
    public void readToCollection() {
        if (musicBandDao != null) {
            List<MusicBand> musicBandList = musicBandDao.read();
            for (MusicBand m : musicBandList) {
                validateAndAddToCollection(m);
            }
        }
    }

    @Deprecated
    public void readToCollection(YamlReader yamlReader) {
        try {
            MusicBand[] yaml = yamlReader.read(System.getenv("YamlFile"));
            if (yaml != null) {
                for (MusicBand m : yaml) {
                    validateAndAddToCollection(m);
                }
            }
        } catch (FileNotFoundException e) {
            printer.printNextLine("Что-то пошло не так во время загрузки коллекции");
        }

    }

    public synchronized boolean addMusicBandToDB(MusicBand musicBand) {
        int id = musicBandDao.create(musicBand);
        if (id != MusicBandDaoImpl.ERROR) {
            musicBand.setId(id);
            musicBandLinkedList.add(musicBand);
            return true;
        }
        return false;
    }


    /**
     * Clears collection
     */
    public Response clearCollection(Request request) {
        User user = creatorManager.findUserByCredentials(request.getCredential());
        Optional<MusicBand> musicBand = musicBandLinkedList.stream().filter(x -> x.getCreatorId() == user.getId()).findFirst();
        if (musicBand.isPresent()) {
            synchronized (this) {
                if (musicBandDao.deleteAll(musicBand.get()) != DAO.ERROR) {
                    musicBandLinkedList.removeIf(m -> m.getCreatorId() == musicBand.get().getCreatorId());
                }
                return new ResponseFactory().createResponse("Все возможные объекты были удалены");
            }
        }
        return new ResponseFactory().createResponse("Вы пока что не создали ни одного объекта");
    }


    /**
     * Removes object by id and creator_id
     *
     * @param request
     * @param id
     * @return
     */
    public Response removeFromCollection(Request request, int id) {
        Optional<MusicBand> targetMusicBand = findModelByIdAndCreatorId(request, id);
        if (targetMusicBand.isPresent()) {
            synchronized (this) {
                if (musicBandDao.deleteById(targetMusicBand.get()) != DAO.ERROR) {
                    musicBandLinkedList.remove(findModelById(id));
                    return new ResponseFactory().createResponse("Объект с id " + targetMusicBand.get().getId() + " успешно удален!");
                }
            }
        }
        return new ResponseFactory().createResponse("Объекта с id " + id + " не существует или вы пытаетесь удалить объект, который был создан не вами");
    }

    public Response updateElementInCollection(MusicBand musicBand, Integer userInput, Request request) {
        Optional<MusicBand> targetMusicBand = findModelByIdAndCreatorId(request, userInput);
        if (targetMusicBand.isPresent()) {
            synchronized (this) {
                request.getRequestBodyMusicBand().getMusicBand().setCreatorId(targetMusicBand.get().getCreatorId());
                request.getRequestBodyMusicBand().getMusicBand().setId(targetMusicBand.get().getId());
                if (musicBandDao.update(request.getRequestBodyMusicBand().getMusicBand()) != DAO.ERROR) {
                    musicBand.updateElement(request.getRequestBodyMusicBand().getMusicBand());
                    return new ResponseFactory().createResponse("Объект с id: " + request.getRequestBody().getArgs()[0] + " успешно обновлен");
                }
            }
        }
        return new ResponseFactory().createResponse("Объекта с id " + userInput + " нет в коллекции или вы пытаетесь обновить элемент, который вам не принадлежит");
    }

    /**
     * Finds object by id
     *
     * @param id
     * @return
     */
    public MusicBand findModelById(Integer id) {
        return musicBandLinkedList.stream().filter(musicBand -> musicBand.getId() == id).findFirst().orElse(null);
    }

    public Optional<MusicBand> findModelByIdAndCreatorId(Request request, int id) {
        User user = creatorManager.findUserByCredentials(request.getCredential());
        return musicBandLinkedList.stream().filter(k -> k.getCreatorId() == user.getId()).filter(v -> v.getId() == id).findAny();
    }

    public Response removeLower(Request request, int count) {
        User user = creatorManager.findUserByCredentials(request.getCredential());
        List<MusicBand> usersList = musicBandLinkedList.stream().filter(x -> x.getCreatorId() == user.getId() && x.getNumberOfParticipants() < count).toList();
        if (usersList.size() == 0) {
            return new ResponseFactory().createResponse("Элементов, меньших чем указано, не было найдено или вы не создали ни одного объекта");
        } else {
            synchronized (this) {
                for (MusicBand m : usersList) {
                    if (musicBandDao.deleteById(m) != DAO.ERROR) {
                        musicBandLinkedList.remove(m);
                    }
                }
                return new ResponseFactory().createResponse("Элементы, меньшие чем указаны успешно удалены");
            }
        }
    }

    /**
     * Getting localDate for collection initialization
     *
     * @return LocalDateTime object with current date
     */
    public LocalDateTime getLocalDate() {
        return creationDate;
    }

    /**
     * Getting collection
     *
     * @return LinkedList with MusicBand objects
     */
    public LinkedList<MusicBand> getMusicBandLinkedList() {
        return musicBandLinkedList;
    }

    public HashSet<Number> getIdContainer() {
        return idContainer;
    }
}
