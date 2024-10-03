package com.venikovdi.carpark.view;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.venikovdi.carpark.data.dto.GPSDto;
import com.venikovdi.carpark.entity.Drive;
import com.venikovdi.carpark.repository.DriveRepository;
import com.venikovdi.carpark.service.DriveService;

import java.util.List;
import java.util.Optional;

@Route("route-view")
public class RouteView extends VerticalLayout implements HasUrlParameter<Integer> {

    private final DriveService driveService;
    private final DriveRepository driveRepository;

    private Integer driveId;

    // Ваш API ключ для Yandex Static API
    private static final String YANDEX_API_KEY = "4deec4dd-83bb-4556-bf47-d81f7957e5a7";

    public RouteView(DriveService driveService, DriveRepository driveRepository) {
        this.driveService = driveService;
        this.driveRepository = driveRepository;

        // Добавление компонента для карты
        Image mapImage = new Image();
        mapImage.setWidth("100%");
        mapImage.setHeight("500px");
        add(mapImage);

        // Установка параметра driveId
        setParameter(null, driveId);  // Для примера driveId=1
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Integer driveId) {
        if (driveId != null) {
            this.driveId = driveId;
            List<GPSDto> track = getTrackForDrive(driveId);  // Получаем трек
            String imageUrl = generateStaticMapUrl(track);  // Генерируем URL для Yandex Static API
            ((Image)getComponentAt(0)).setSrc(imageUrl);  // Устанавливаем URL как источник для изображения
        }
    }

    // Генерация URL для Yandex Static API
    private String generateStaticMapUrl(List<GPSDto> track) {
        StringBuilder coordinates = new StringBuilder();

        // Формат координат: широта, долгота, разделённые тильдой ~
        for (GPSDto point : track) {
            coordinates.append(point.geom().getX()).append(",").append(point.geom().getY()).append(",");
        }
        coordinates.deleteCharAt(coordinates.length() - 1);  // Убираем последнюю тильду

        // Генерация запроса к Yandex Static API
        return "https://static-maps.yandex.ru/v1?lang=ru_RU"
                + "&ll=" + track.get(0).geom().getX() + "," + track.get(0).geom().getY()  // Центр карты
                + "&pt=" + track.get(0).geom().getX() + "," + track.get(0).geom().getY()
                + ",pm2am~" + track.get(track.size()-1).geom().getX() + "," + track.get(track.size()-1).geom().getY()
                + ",pm2bm"
                + "&size=650,450"  // Размер изображения
                + "&pl=" + coordinates
                + "&apikey=" + YANDEX_API_KEY;// Полилиния
    }

    // Метод для получения трека поездки
    private List<GPSDto> getTrackForDrive(Integer driveId) {
        Optional<Drive> drive = driveRepository.findById(Long.valueOf(driveId));
        if (drive.isPresent()){
            return driveService.getGPSDrives(drive.get().getVehicle().getId(), drive.get().getStart(), drive.get().getEnd());
        }
        return List.of();
    }
}
