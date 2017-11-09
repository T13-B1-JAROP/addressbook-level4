//@@author arnollim
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.insurance.UniqueLifeInsuranceList;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Prints the list of contacts, along with any associated
 * insurance policies where the contact is involved in,
 * into a printable, readable .txt file.
 */
public class PrintCommand extends Command {

    public static final String[] COMMAND_WORDS = {"print"};
    public static final String COMMAND_WORD = "print";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves the addressbook into a .txt file named by you for your viewing.\n"
            + "Example: " + COMMAND_WORD + " filename\n"
            + "file can then be found in the in data/ folder as data/filename.txt";

    public static final String MESSAGE_SUCCESS = "Address Book has been saved!\n"
            + "Find your Address Book in the %1$s.txt file you created in data/%1$s.txt.";

    private final String fileName;

    public PrintCommand(String fileName) {
        requireNonNull(fileName);

        this.fileName = fileName;
    }


    @Override
    public CommandResult execute() {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        List<String> lines = new ArrayList<>();
        String timeStamp = new SimpleDateFormat("dd/MM/YYYY" + " " + "HH:mm:ss").format(new Date());
        lines.add("LISA was last updated on: " + timeStamp + "\n\n");

        lines.add("There are " + lastShownList.size() + " contacts in LISA\n\n");

        int personIndex = 1;
        for (ReadOnlyPerson person: lastShownList) {
            String entry = personIndex + ". " + person.getAsParagraph();
            lines.add(entry);
            lines.add("\n" + person.getName().fullName
                    + " is a personnel involved in the following insurance policies:\n");

            UniqueLifeInsuranceList insurances = person.getLifeInsurances();
            int insuranceIndex = 1;
            for (ReadOnlyInsurance insurance: insurances) {
                lines.add("Insurance Policy " + insuranceIndex + ": =========");
                String owner = insurance.getOwner().getName();
                String insured = insurance.getInsured().getName();
                String beneficiary = insurance.getBeneficiary().getName();
                String premium = insurance.getPremium().toString();
                String signingDate = insurance.getSigningDateString();
                String expiryDate = insurance.getExpiryDateString();
                lines.add("Owner: " + owner + "\n"
                        + "Insured: " + insured + "\n"
                        + "Beneficiary: " + beneficiary + "\n"
                        + "Premium: " + premium + "\n"
                        + "Signing Date: " + signingDate + "\n"
                        + "Expiry Date: " + expiryDate
                );
                lines.add("===========================\n");
                insuranceIndex++;
            }
            lines.add("--------End of " + person.getName().fullName + "'s profile");
            lines.add("\n");
            personIndex++;
        }

        Path file = Paths.get("data/" + fileName + ".txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.fileName));
    }

}