package dev.selena.luacore.utils.data;

import com.google.common.annotations.Beta;
import dev.selena.luacore.exceptions.NoUserDataFolderException;
import dev.selena.luacore.exceptions.NoUserJsonFoundException;
import dev.selena.luacore.exceptions.NotUserFolderException;
import dev.selena.luacore.utils.config.FileManager;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Beta
public class UserDataManager {

    public static String folderName;
    private final Map<UUID, Object> userMap;

    /**
     * Used for setting up the manager class.
      * @param baseFolder The base folder for user data to be stored in
     */
    public UserDataManager(String baseFolder) {
        UserDataManager.folderName = baseFolder;
        File folder = FileManager.folder(folderName);
        if (!folder.exists())
            folder.mkdirs();
        userMap = new TreeMap<>();
    }

    /**
     * Used for getting the users data path as a string.
     * @param uuid The users UUID
     * @return The folder path
     */
    public String getUserFolderPath(UUID uuid) {
        return FileManager.folderPath(folderName + File.separator + uuid.toString());
    }

    /**
     * Used for getting the initialized data folder class
     * @param userFolderClass The class that extends UserFolder
     * @param uuid The users UUID
     * @return The initialized class
     * @param <T> The type of the class
     * @throws NoUserDataFolderException Thrown when the folder has not been set up make sure to call {@link UserDataManager#createUserDataFolder(UUID)}
     * @throws NoSuchMethodException Thrown on failed class initialization
     * @throws InvocationTargetException Thrown on failed class initialization
     * @throws InstantiationException Thrown on failed class initialization
     * @throws IllegalAccessException Thrown on failed class initialization
     * @throws NoUserJsonFoundException Thrown when you have not called the {@link UserFolder#loadData(Class, String)} method.
     */
    @SuppressWarnings("unchecked")
    public <T> T getUserDataFolder(Class<T> userFolderClass, UUID uuid) throws NoUserDataFolderException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoUserJsonFoundException {
        if (userMap.containsKey(uuid)) {
            return (T) userMap.get(uuid);
        }
        File parent = new File(getUserFolderPath(uuid));
        if (!parent.exists()) {
            throw new NoUserDataFolderException("There is no user data for the UUID: " + uuid + ". Please be sure to call UserDataManager#createUserDataFolder(uuid)");
        }
        T userClass = userFolderClass.getConstructor().newInstance();
        if (!(userClass instanceof UserFolder)) {
            throw new NotUserFolderException("The class " + userClass.getClass().getName() + " Is not instance of UserData");
        }
        ((UserFolder) userClass).init(uuid);
        userMap.put(uuid, userClass);

        return userClass;

    }

    /**
     * Used for initial creation of the user folder.
     * @param uuid the users UUID
     */
    public void createUserDataFolder(UUID uuid) {
        File folder = new File(getUserFolderPath(uuid));
        if (!folder.exists())
            folder.mkdirs();
    }

}
