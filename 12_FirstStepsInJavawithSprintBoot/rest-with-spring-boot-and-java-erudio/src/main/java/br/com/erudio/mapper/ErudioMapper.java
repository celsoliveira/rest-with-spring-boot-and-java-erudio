package br.com.erudio.mapper;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
//import com.github.dozermapper.core.DozerBeanMapperBuilder;
//import com.github.dozermapper.core.Mapper;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.model.Person;

public class ErudioMapper {
	
	//Substituimos o Dozzer pelo ModelMapper em virtude do Dozzer 
	// possivelmente ser descontinuado no futuro.
	
	//private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	private static ModelMapper mapper = new ModelMapper();
	
	static {
		mapper.createTypeMap(
				Person.class, 
				PersonVO.class)
		.addMapping(Person::getId, PersonVO::setKey);
		
		mapper.createTypeMap(
				PersonVO.class, 
				Person.class)
		.addMapping(PersonVO::getKey, Person::setId);		
	}
	
	public static <O, D> D parseObject(O origin, Class<D> destination){
		return mapper.map(origin,  destination);
	}
	
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination){
		List<D> destinationObjects =  new ArrayList<D>();
		
		for (O o : origin) {
			destinationObjects.add(mapper.map(o, destination));
		}
		return destinationObjects;
	}


}
