/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author excellent
 */
public class TestDAO {
    @Test
    public void test() {
        try(Connection con = KoneksiPusat.getConnection()){
        } catch (Exception ex) {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
