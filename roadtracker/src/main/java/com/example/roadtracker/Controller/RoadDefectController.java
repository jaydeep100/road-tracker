package com.example.roadtracker.Controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/defects")
public class RoadDefectController {

    private final JdbcTemplate jdbc;

    public RoadDefectController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @PostMapping
    public String addDefect(@RequestBody String json) {

        String sql =
                "SELECT public.road_defect_add(?::jsonb)";

        return jdbc.queryForObject(
                sql,
                (rs, rowNum) -> rs.getString(1),
                json
        );
    }

    @GetMapping
    public String getAllDefects() {
        String sql = """
        SELECT json_agg(row_to_json(d))
        FROM (
            SELECT id, description, latitude, longitude, created_at
            FROM road_defects
            ORDER BY id
        ) d
        """;

        return jdbc.queryForObject(sql, String.class);
    }

    @GetMapping("/{id}")
    public String getDefectById(@PathVariable int id) {

        String sql = """
        SELECT row_to_json(d)
        FROM (
            SELECT id, description, latitude, longitude, created_at
            FROM road_defects
            WHERE id = ?
        ) d
        """;

        return jdbc.queryForObject(
                sql,
                (rs, rowNum) -> rs.getString(1),
                id
        );
    }

    @GetMapping("/nearby")
    public String getNearbyDefects(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam double radius) {
        String sql =
                "SELECT get_nearby_defects(?, ?, ?)::text";

        return jdbc.queryForObject(
                sql,
                String.class,
                lat,
                lon,
                radius
        );

    }

}