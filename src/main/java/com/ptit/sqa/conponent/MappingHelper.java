package com.ptit.sqa.conponent;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MappingHelper {

        private static ModelMapper modelMapper = new ModelMapper();

        public static <D, T> D map(final T entity, Class<D> outClass){
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            return modelMapper.map(entity, outClass);
        }

        public static  <D, T> List<D> mapList(final Collection<T> entities, Class<D> outClass){
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            return entities.stream()
                    .map(entity -> map(entity, outClass))
                    .collect(Collectors.toList());
        }
}
