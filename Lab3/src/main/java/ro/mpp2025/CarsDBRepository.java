package ro.mpp2025;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ro.mpp2025.CarsDBRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {

        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Car> cars=new ArrayList<>();
        try(PreparedStatement prepStnt = con.prepareStatement("select * from cars where manufacturer=?")) {
            prepStnt.setString(1,manufacturerN);
            try(ResultSet rs = prepStnt.executeQuery()) {
                while (rs.next()) {
                    int id=rs.getInt("id");
                    String manufacturer=rs.getString("manufacturer");
                    String model=rs.getString("model");
                    int year=rs.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {

        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Car> cars=new ArrayList<>();
        try(PreparedStatement prepStnt = con.prepareStatement("select * from cars where year>=? and year<=?")) {
            prepStnt.setInt(1,min);
            prepStnt.setInt(2,max);
            try(ResultSet rs = prepStnt.executeQuery()) {
                while (rs.next()) {
                    int id=rs.getInt("id");
                    String manufacturer=rs.getString("manufacturer");
                    String model=rs.getString("model");
                    int year=rs.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("Adding element: {}", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement prepStnt = con.prepareStatement("insert into cars (manufacturer, model, year) values (?,?,?)")) {
            prepStnt.setString(1, elem.getManufacturer());
            prepStnt.setString(2, elem.getModel());
            prepStnt.setInt(3, elem.getYear());
            int result=prepStnt.executeUpdate();
            logger.trace("Inserted element: {}", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Car elem) {

        logger.traceEntry("Updating element: {}", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement prepStnt = con.prepareStatement("update table cars set year=?, manufacturer=?, model=? where id=?")) {
            prepStnt.setInt(1, elem.getYear());
            prepStnt.setString(2, elem.getManufacturer());
            prepStnt.setString(3, elem.getModel());
            prepStnt.setInt(4, integer);
            int result = prepStnt.executeUpdate();
            logger.trace("Updated element: {}", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();

    }

    @Override
    public Iterable<Car> findAll() {

        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Car> cars=new ArrayList<>();
        try(PreparedStatement prepStnt = con.prepareStatement("select * from cars")) {
            try(ResultSet rs = prepStnt.executeQuery()) {
                while (rs.next()) {
                    int id=rs.getInt("id");
                    String manufacturer=rs.getString("manufacturer");
                    String model=rs.getString("model");
                    int year=rs.getInt("year");
                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit(cars);
        return cars;
    }
}