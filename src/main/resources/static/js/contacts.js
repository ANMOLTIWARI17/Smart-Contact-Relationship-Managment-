console.log("Console Js");
const baseURl = "http://localhost:8081";
const viewContactModal = document.getElementById("view_contact_modal");
// options with default values
const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onHide: () => {
    console.log("modal is hidden");
  },
  onShow: () => {
    console.log("modal is shown");
  },
  onToggle: () => {
    console.log("modal has been toggled");
  },
};

// instance options object
const instanceOptions = {
  id: "view_contact_modal",
  override: true,
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
  contactModal.show();
}

function closeContactModal() {
  contactModal.hide();
}

// async function loadContactdata(id) {
//   //function call to load data
//   console.log(id);
//   try {
//     const data = await (
//       await fetch(`http://localhost:8081/api/contacts/${id}`)
//     ).json();
//     // console.log(data);
//     document.querySelector("#contact_name").innerHTML = data.name;
//     document.querySelector("#contact_email").innerHTML = data.email;
//     openContactModal();
//   } catch (error) {
//     console.log(error);
//   }
// }

async function loadContactdata(id) {
  try {
    const response = await fetch(`${baseURl}/api/contacts/${id}`);
    const data = await response.json();

    console.log(data); // Check the data structure in the console

    // Update the modal fields with fetched data
    document.getElementById("contact_name").textContent =
      data.name || "No name available";
    document.getElementById("contact_email").textContent =
      data.email || "No email available";
    document.getElementById("contact_address").textContent =
      data.address || "No address available";
    document.getElementById("contact_about").textContent =
      data.description || "No information available"; // Corrected this line
    document.getElementById("contact_favorite").textContent = data.favorite
      ? "This is your favorite contact"
      : "Not a favorite contact";
    document.getElementById("contact_website").textContent =
      data.websiteLink || "No website available"; // Corrected this line
    document.getElementById("contact_website").href = data.websiteLink || "#"; // Corrected this line
    document.getElementById("contact_linkedIn").textContent =
      data.linkedInLink || "No LinkedIn available"; // Corrected this line
    document.getElementById("contact_linkedIn").href = data.linkedInLink || "#"; // Corrected this line

    document.getElementById("contact_picture").src =
      data.picture || "default_picture_url.jpg"; // Set a default picture if none exists

    // Open the modal after data is loaded
    openContactModal();
  } catch (error) {
    console.error("Error loading contact data:", error);
  }
}

//delete contact
async function deleteContact(id) {
  Swal.fire({
    title: "Do you want to delete contact?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Delete",
  }).then((result) => {
    /* Read more about isConfirmed, isDenied below */
    if (result.isConfirmed) {
      const url = `${baseURl}/user/contacts/delete/` + id; // Corrected the URL
      window.location.replace(url);
    } else if (result.isDenied) {
      Swal.fire("Not Deleted", "", "info");
    }
  });
}
