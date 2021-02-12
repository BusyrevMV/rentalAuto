package com.ertelecom.carrental.service.impl;

import com.ertelecom.carrental.factory.HibernateSessionFactory;
import com.ertelecom.carrental.request.RentalHistoryAddRequest;
import com.ertelecom.carrental.request.RentalHistoryCloseRequest;
import com.ertelecom.carrental.model.entity.Car;
import com.ertelecom.carrental.model.entity.RentalHistory;
import com.ertelecom.carrental.model.view.RentalHistoryAvgReportByBrandView;
import com.ertelecom.carrental.model.view.RentalHistoryAvgReportByModelView;
import com.ertelecom.carrental.model.view.RentalHistoryView;
import com.ertelecom.carrental.model.filter.RentalFilter;
import com.ertelecom.carrental.repository.CarRepository;
import com.ertelecom.carrental.repository.RentalHistoryRepository;
import com.ertelecom.carrental.service.IRentalHistoryService;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("IRentalHistory")
public class RentalHistoryServiceImpl implements IRentalHistoryService {
    @Autowired
    private RentalHistoryRepository rentalHistoryRepository;

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<RentalHistory> load(){
        return rentalHistoryRepository.findAll();
    }

    @Override
    public List<RentalHistoryView> loadByCarBrandAndCarModel(RentalFilter rentalFilter){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        StringBuilder sb = new StringBuilder()
                .append("SELECT rh.id, rh.dateBeg, rh.dateEnd, puBeg.surname, puBeg.name, puBeg.patronymic, puBeg.login, ")
                .append("puEnd.surname, puEnd.name, puEnd.patronymic, puEnd.login, cl.surname AS clientSurname, ")
                .append("cl.name, cl.patronymic, cl.driverNumber, cb.name, cm.name, c.plateNumber, rpBeg.name, rpEnd.name ")
                .append("FROM RentalHistory rh ")
                .append("INNER JOIN Car c ON c.id = rh.carId ")
                .append("INNER JOIN CarModel cm ON cm.id = c.carModelId ")
                .append("INNER JOIN CarBrand cb ON cb.id = cm.carBrandId ")
                .append("INNER JOIN Client cl ON cl.id = rh.clientId ")
                .append("INNER JOIN ProgUser puBeg ON puBeg.id = rh.progUserIdBeg ")
                .append("LEFT JOIN ProgUser puEnd ON puEnd.id = rh.progUserIdEnd ")
                .append("INNER JOIN RentalPoint rpBeg ON rpBeg.id = rh.rentalPointIdBeg ")
                .append("LEFT JOIN RentalPoint rpEnd ON rpEnd.id = rh.rentalPointIdEnd ")
                .append("WHERE 1=1 ")
                .append(rentalFilter.carId != null && rentalFilter.carId > 0 ? String.format("AND c.id = %s ", rentalFilter.carId) : "")
                .append(rentalFilter.carBrand != null && rentalFilter.carBrand.length > 0
                        ? String.format("AND cm.carBrandId in (%s) ",
                            Arrays.toString(rentalFilter.carBrand).replaceAll("\\[|\\]|\\s", ""))
                        : "")
                .append(rentalFilter.carModel != null && rentalFilter.carModel.length > 0
                        ? String.format("AND cm.id in (%s) ",
                            Arrays.toString(rentalFilter.carModel).replaceAll("\\[|\\]|\\s", ""))
                        : "")
                .append("ORDER BY rh.dateBeg DESC ");

        Query query = session.createQuery(sb.toString());

        List list = query.list();
        List<RentalHistoryView> result = (List<RentalHistoryView>) list
                .stream()
                .map((s)-> {
                    Object[] row = (Object[])s;
                    RentalHistoryView view = new RentalHistoryView();
                    int i = 0;
                    view.id = (long)row[i++];
                    view.setDateBeg((Timestamp)row[i++]);
                    view.setDateEnd((Timestamp)row[i++]);
                    view.progUserBegFIO = (String)row[i++] + " " + (String)row[i++] + " " + (String)row[i++];
                    view.progUserBegUsername = (String)row[i++];
                    view.progUserEndFIO = (String)row[i++] + " " + (String)row[i++] + " " + (String)row[i++];
                    view.progUserEndUsername = (String)row[i++];
                    view.clientFIO = (String)row[i++] + " " + (String)row[i++] + " " + (String)row[i++];
                    view.clientDriverNumber = (String)row[i++];
                    view.carBrand = (String)row[i++];
                    view.carModel = (String)row[i++];
                    view.carPlateNumber = (String)row[i++];
                    view.rentalPointBeg = (String)row[i++];
                    view.rentalPointEnd = (String)row[i++];
                    return view;
                })
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public List<RentalHistoryAvgReportByBrandView> loadReportAvgRentalByCarBrand(RentalFilter rentalFilter){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        StringBuilder sb = new StringBuilder()
                .append("SELECT rp.name, cb.name, AVG(EXTRACT(epoch FROM (rh.dateEnd - rh.dateBeg)))/3600 ")
                .append("FROM RentalHistory rh ")
                .append("INNER JOIN Car c ON c.id = rh.carId ")
                .append("INNER JOIN CarModel cm ON cm.id = c.carModelId ")
                .append("INNER JOIN CarBrand cb ON cb.id = cm.carBrandId ")
                .append("INNER JOIN RentalPoint rp ON rp.id = rh.rentalPointIdBeg ")
                .append("WHERE rh.dateEnd IS NOT NULL ")
                .append(rentalFilter.carId != null && rentalFilter.carId > 0 ? String.format("AND c.id = %s ", rentalFilter.carId) : "")
                .append(rentalFilter.carBrand != null && rentalFilter.carBrand.length > 0
                        ? String.format("AND cm.carBrandId in (%s) ",
                        Arrays.toString(rentalFilter.carBrand).replaceAll("\\[|\\]|\\s", ""))
                        : "")
                .append("GROUP BY rh.rentalPointIdBeg, rp.id, cb.id ")
                .append("ORDER BY rp.name, cb.name ");

        Query query = session.createQuery(sb.toString());

        List list = query.list();
        List<RentalHistoryAvgReportByBrandView> result = (List<RentalHistoryAvgReportByBrandView>) list
                .stream()
                .map((s)-> {
                    Object[] row = (Object[])s;
                    RentalHistoryAvgReportByBrandView view = new RentalHistoryAvgReportByBrandView();
                    int i = 0;
                    view.rentalPoint = (String)row[i++];
                    view.carBrand = (String)row[i++];
                    view.avgTime = (Double) row[i++];
                    return view;
                })
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public List<RentalHistoryAvgReportByModelView> loadReportAvgRentalByCarModel(RentalFilter rentalFilter){
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        StringBuilder sb = new StringBuilder()
                .append("SELECT rp.name, cb.name, cm.name, AVG(EXTRACT(epoch FROM (rh.dateEnd - rh.dateBeg)))/3600 ")
                .append("FROM RentalHistory rh ")
                .append("INNER JOIN Car c ON c.id = rh.carId ")
                .append("INNER JOIN CarModel cm ON cm.id = c.carModelId ")
                .append("INNER JOIN CarBrand cb ON cb.id = cm.carBrandId ")
                .append("INNER JOIN RentalPoint rp ON rp.id = rh.rentalPointIdBeg ")
                .append("WHERE rh.dateEnd IS NOT NULL ")
                .append(rentalFilter.carId != null && rentalFilter.carId > 0 ? String.format("AND c.id = %s ", rentalFilter.carId) : "")
                .append(rentalFilter.carBrand != null && rentalFilter.carBrand.length > 0
                        ? String.format("AND cm.carBrandId in (%s) ",
                        Arrays.toString(rentalFilter.carBrand).replaceAll("\\[|\\]|\\s", ""))
                        : "")
                .append(rentalFilter.carModel != null && rentalFilter.carModel.length > 0
                        ? String.format("AND cm.id in (%s) ",
                        Arrays.toString(rentalFilter.carModel).replaceAll("\\[|\\]|\\s", ""))
                        : "")
                .append("GROUP BY rh.rentalPointIdBeg, rp.id, cb.id, cm.id ")
                .append("ORDER BY rp.name, cb.name, cm.name ");

        Query query = session.createQuery(sb.toString());

        List list = query.list();
        List<RentalHistoryAvgReportByModelView> result = (List<RentalHistoryAvgReportByModelView>) list
                .stream()
                .map((s)-> {
                    Object[] row = (Object[])s;
                    RentalHistoryAvgReportByModelView view = new RentalHistoryAvgReportByModelView();
                    int i = 0;
                    view.rentalPoint = (String)row[i++];
                    view.carBrand = (String)row[i++];
                    view.carModel = (String)row[i++];
                    view.avgTime = (Double)row[i++];
                    return view;
                })
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public Optional<RentalHistory> loadById(long id){
        return rentalHistoryRepository.findById(id);
    }

    @Transactional
    public RentalHistory add(RentalHistoryAddRequest rentalHistoryAddRequest) throws SQLException {
        Optional<Car> car = carRepository.findById(rentalHistoryAddRequest.getCarId());
        if (!car.isPresent()){
            throw new SQLException("Машина не найдена");
        }

        if (car.get().getRentalPointId() == null
                || !car.get().getRentalPointId().equals(rentalHistoryAddRequest.getRentalPointIdBeg())){
            throw new SQLException("Машина отсутствует в данном пункте проката");
        }

        RentalHistory rentalHistory = rentalHistoryAddRequest.fillToEntity(new RentalHistory());

        if (rentalHistory.getDateBeg() == null) {
            rentalHistory.setDateBeg(new Timestamp(System.currentTimeMillis()));
        }

        rentalHistoryRepository.save(rentalHistory);
        car.get().setRentalPointId(null);
        carRepository.save(car.get());

        return rentalHistory;
    }

    @Transactional
    public RentalHistory close(RentalHistoryCloseRequest rentalHistoryCloseRequest) throws SQLException {
        Optional<RentalHistory> rentalHistory = rentalHistoryRepository.findById(rentalHistoryCloseRequest.getId());

        if(!rentalHistory.isPresent()){
            throw new SQLException("Запись не найдена");
        }

        if(rentalHistory.get().getDateEnd() != null){
            throw new SQLException("Запись закрыта, редактирование невозможно");
        }

        if(rentalHistoryCloseRequest.getDateEnd() == null
                || rentalHistoryCloseRequest.getRentalPointIdEnd() == null
                || rentalHistoryCloseRequest.getProgUserIdEnd() == null){
            throw new SQLException("При закрытие аренды принявший сотрудник, дата закрытия и пункт сдачи должны быть заполнены");
        }

        rentalHistoryCloseRequest.fillToEntity(rentalHistory.get());
        if (rentalHistory.get().getDateEnd() == null) {
            rentalHistory.get().setDateBeg(new Timestamp(System.currentTimeMillis()));
        }

        if(rentalHistory.get().getDateEnd().compareTo(rentalHistory.get().getDateBeg()) <= 0){
            throw new SQLException("Дата завершения аренды не может быть меньше или равна дате начала аренды");
        }

        Car car = carRepository.getOne(rentalHistory.get().getCarId());
        car.setRentalPointId(rentalHistory.get().getRentalPointIdEnd());
        carRepository.save(car);

        rentalHistoryRepository.save(rentalHistory.get());

        return rentalHistory.get();
    }

    @Override
    public boolean deleteById(long id){
        if (rentalHistoryRepository.findById(id).isPresent()) {
            rentalHistoryRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
