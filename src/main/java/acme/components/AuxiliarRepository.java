
package acme.components;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.currency_cache.CurrencyCache;
import acme.entities.systemconf.SystemConfiguration;

@Repository
public interface AuxiliarRepository extends AbstractRepository {

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();

	@Query("select cc from CurrencyCache cc where cc.origenCurrency = :orgCurrency and cc.destinationCurrency = :dstCurrency")
	CurrencyCache getCurrencyCacheByChange(String orgCurrency, String dstCurrency);
}
