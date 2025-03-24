package lk.ijse.gdse71.orm_course_work.bo.custom.impl;

import lk.ijse.gdse71.orm_course_work.bo.custom.ProgramsBo;
import lk.ijse.gdse71.orm_course_work.dao.DaoFactory;
import lk.ijse.gdse71.orm_course_work.dao.custom.ProgrmasDao;
import lk.ijse.gdse71.orm_course_work.dto.ProgramDto;
import lk.ijse.gdse71.orm_course_work.entity.TheraphyProgram;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgrmasBoImpl implements ProgramsBo {
    private final ProgrmasDao progrmasDao = DaoFactory.getInstance().getDAO(DaoFactory.DAOType.PROGRAM);

    @Override
    public List<String> getAllPrograms() throws SQLException {
        return progrmasDao.getAllPrograms();
    }

    @Override
    public String getNextId() throws SQLException {
        String id = progrmasDao.getNextId();

        if (id != null) {
            String lastId = id.substring(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("MT%03d", newIdIndex);
        }
        return "MT001";
    }

    @Override
    public List<ProgramDto> getAll() throws SQLException {
        List<TheraphyProgram> all = progrmasDao.getAll();
        List<ProgramDto> programDtos = new ArrayList<>();

        for (TheraphyProgram program : all) {
            ProgramDto programDto = new ProgramDto();
            programDto.setTheraphy_pro_id(program.getTheraphy_pro_id());
            programDto.setName(program.getName());
            programDto.setDuration(program.getDuration());
            programDto.setFee(program.getFee());
            programDto.setDescription(program.getDescription());
            programDtos.add(programDto);
        }
        return programDtos;
    }

    @Override
    public boolean save(ProgramDto programDto) throws SQLException {
        TheraphyProgram program = new TheraphyProgram();
        program.setTheraphy_pro_id(programDto.getTheraphy_pro_id());
        program.setName(programDto.getName());
        program.setDuration(programDto.getDuration());
        program.setFee(programDto.getFee());
        program.setDescription(programDto.getDescription());

        return progrmasDao.save(program);
    }

    @Override
    public boolean update(ProgramDto programDto) throws SQLException {
        TheraphyProgram program = new TheraphyProgram();
        program.setTheraphy_pro_id(programDto.getTheraphy_pro_id());
        program.setName(programDto.getName());
        program.setDuration(programDto.getDuration());
        program.setFee(programDto.getFee());
        program.setDescription(programDto.getDescription());

        return progrmasDao.update(program);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return progrmasDao.delete(id);
    }
}
