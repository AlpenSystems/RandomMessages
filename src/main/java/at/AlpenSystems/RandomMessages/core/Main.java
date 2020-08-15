/**
 * Class Main
 * @author David-H-Dev
 *
 * last changes:
 * @date 15.08.2020
 */

package at.AlpenSystems.RandomMessages.core;

import at.AlpenSystems.RandomMessages.utils.BotManager;
import at.AlpenSystems.RandomMessages.utils.sql.DBStatement;

public class Main {

    private static final DBStatement dbStatement = new DBStatement();

    public static void main(String[] args) {
        BotManager.startBot();
        dbStatement.checkDBConnection();
    }
}