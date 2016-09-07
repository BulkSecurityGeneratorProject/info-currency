package pl.lodz.p.zzpj.managers;

import pl.lodz.p.zzpj.vm.ConverterVM;

/**
 * Manager that provides currency converter.
 */
public interface Converter {
    String convert(ConverterVM converterVM);
}
